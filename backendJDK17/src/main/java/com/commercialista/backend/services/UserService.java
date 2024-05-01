package com.commercialista.backend.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.commercialista.backend.controllers.AuthController;
import com.commercialista.backend.enums.StatiAccountEnum;
import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.ERole;
import com.commercialista.backend.models.Role;
import com.commercialista.backend.models.User;
import com.commercialista.backend.payload.request.SignupRequest;
import com.commercialista.backend.repository.AccountRepository;
import com.commercialista.backend.repository.RoleRepository;
import com.commercialista.backend.repository.UserRepository;
import com.commercialista.backend.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
    @Autowired
    private EmailSenderService emailSenderService;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
	public void registerUserAndAccount(SignupRequest signUpRequest, String authorizationHeader) throws Exception {
	    try {
	        User newUser = registerUser(signUpRequest, authorizationHeader);
	        Account registeredAccount = registerAccount(newUser);
	        emailSenderService.sendEmailConfermaRegistrazione(registeredAccount, newUser);
	    } catch (Exception e) {
	        // Logga l'eccezione per identificarne la causa
	        LOGGER.error("Errore durante la registrazione dell'utente e dell'account", e);
	        throw e; // Rilancia l'eccezione per consentire il rollback della transazione
	    }
	}
	
	
	private User registerUser(SignupRequest signUpRequest, String authorizationHeader) throws Exception {


		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			// la chiamata senza autenticazione consente di censire solo un utente normale,
			// quindi se si sta tentando di censire un ADMIN o MODERATOR, allora verificare ci sia un token valido di un admin
			checkPermissionIfNotStandardRole(authorizationHeader, strRoles);
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		User newUser = userRepository.save(user);
		return newUser;

	}
	
	private void checkPermissionIfNotStandardRole(String authorizationHeader, Set<String> strRoles) throws Exception {
		if (List.of(ERole.ROLE_ADMIN.name(), ERole.ROLE_MODERATOR.name()).stream().anyMatch(strRoles::contains)) {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				// Estrai il token JWT dal valore dell'header Authorization
				String jwtToken = authorizationHeader.substring(7);
				// Effettua il parsing del token JWT e gestisci il comportamento di conseguenza
				if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
					String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
					User operationUser = userRepository.findByUsername(username)
							.orElseThrow(() -> new Exception("Nessuno username trovato appartenente a questo token"));
					if (!operationUser.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet())
							.contains(ERole.ROLE_ADMIN)) {
						throw new Exception("Solo un admin può censire un utente con permessi non standard");
					}

				}
			} else {
				// Token JWT non presente, comportati di conseguenza
				throw new Exception("Solo un admin può censire un utente con permessi non standard");
			}
		}
	}

	
	private Account registerAccount(User newUser) {
		Account newAccount = new Account();
		newAccount.setIdUser(newUser.getId());
		newAccount.setStatus(StatiAccountEnum.NON_ATTIVO);
		newAccount.setVerificationCode(UUID.randomUUID().toString());
		newAccount.setDataPubb(new Date());
		
		Account registredAccount = accountRepository.save(newAccount);
		return registredAccount;
	}
	
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public Account confirmRegistration(Account account) {
		account.setStatus(StatiAccountEnum.ATTIVO);
    	accountRepository.save(account);
    	//invia email di annuncio Pubblicato
    	//emailSenderService.sendEmailAccountVerifiato(account);
    	return account;
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public int removeAccountNonConfermatiDa6gg() {
		List<Long> idsAccountsToRemove = accountRepository.findAccountsNotConfirmedAfter6Days(); 
		for(Long currId: idsAccountsToRemove) {
			roleRepository.deleteByUserId(currId);
			userRepository.deleteById(currId);
			accountRepository.deleteById(currId);
		}
		return idsAccountsToRemove.size();
	}


	public void generaUsername(@Valid SignupRequest signUpRequest) {
		signUpRequest.setUsername(UUID.randomUUID().toString());
	}

}