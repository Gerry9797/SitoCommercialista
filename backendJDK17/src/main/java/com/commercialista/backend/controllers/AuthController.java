package com.commercialista.backend.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercialista.backend.enums.StatiAccountEnum;
import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.User;
import com.commercialista.backend.payload.request.LoginRequest;
import com.commercialista.backend.payload.request.SignupRequest;
import com.commercialista.backend.payload.response.JwtResponse;
import com.commercialista.backend.payload.response.MessageResponse;
import com.commercialista.backend.repository.AccountRepository;
import com.commercialista.backend.repository.RoleRepository;
import com.commercialista.backend.repository.UserRepository;
import com.commercialista.backend.security.jwt.JwtUtils;
import com.commercialista.backend.security.services.UserDetailsImpl;
import com.commercialista.backend.services.UserService;
import com.commercialista.backend.swagger.SwaggerUrlPrinter;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;
	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

		// preleva l'utente con l'email passata in login request
		User user;
		if (loginRequest.getEmail() != null && !loginRequest.getEmail().isBlank()) {
			user = userRepository.findByEmail(loginRequest.getEmail())
					.orElseThrow(() -> new Exception("Credenziali errate"));
		}
//	else if (loginRequest.getUsername() != null && !loginRequest.getUsername().isBlank()) {
//		user = userRepository.findByEmail(loginRequest.getEmail())
//				.orElseThrow(() -> new Exception("Credenziali errate"));
//	}
		else {
			throw new Exception("Inserire nome utente o email validi");
		}

		// Controlla se l'account associato allo User è stato verificato (mail
		// confermata)
		Account accountUser = accountRepository.findById(user.getId())
				.orElseThrow(() -> new Exception("Nessun account associato a questo utente!"));
		if (accountUser.getStatus().equals(StatiAccountEnum.NON_ATTIVO)) {
			throw new Exception("Email non ancora confermata, controlla la tua posta");
		} else if (accountUser.getStatus().equals(StatiAccountEnum.SOSPESO)) {
			throw new Exception("Account sospeso, contattaci se lo ritieni un errore");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest,
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader) throws Exception {
		
		userService.generaUsername(signUpRequest);
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Errore: Username già esistente!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Errore: Email già in uso!"));
		}
		
		try {
			userService.registerUserAndAccount(signUpRequest, authorizationHeader);
		} catch (MessagingException | IOException | TemplateException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error while send Email!"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(e.getMessage()));
		}


		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}


}
