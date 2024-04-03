package com.commercialista.backend.services;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commercialista.backend.enums.StatiAccountEnum;
import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.ERole;
import com.commercialista.backend.models.Role;
import com.commercialista.backend.models.User;
import com.commercialista.backend.payload.request.SignupRequest;
import com.commercialista.backend.repository.AccountRepository;
import com.commercialista.backend.repository.RoleRepository;
import com.commercialista.backend.repository.UserRepository;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
    @Autowired
    private EmailSenderService emailSenderService;

	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public void registerUserAndAccount(SignupRequest signUpRequest) throws MessagingException, IOException, TemplateException {
		User newUser = registerUser(signUpRequest);
		Account registredAccount = registerAccount(newUser);
		emailSenderService.sendEmailConfermaRegistrazione(registredAccount, newUser);
	}
	
	
	private User registerUser(SignupRequest signUpRequest) {


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

}