package com.commercialista.backend.controllers;

import java.io.IOException;
import java.util.List;
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

		// controlla se esiste un utente con email e password indicate e se ha un account attivo sul sistema
		User user = userService.checkLoginAndGetUtente(loginRequest);

		// se è tutto ok, continua con l'autenticazione e genera un token per l'accesso
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
	public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest,
			@RequestHeader(name = "Authorization", required = false) String authorizationHeader) throws Exception {
		
		// gestione dello username nascosta all'utente, viene impostato uno UUID
		userService.generaUsername(signUpRequest);
		
		// controlli su eventuali violazioni che non permettono la registrazione
		ResponseEntity<?> violationResponseEntity = userService.checkViolations(signUpRequest);
		if (violationResponseEntity != null) { // se c'è qualche violazione che non permette la registrazione, ritornare il problema come response entity
			return violationResponseEntity;
		}
		// procedi con la registrazione e l'invio della mail di attivazione account con verifica
		try {
			userService.registerUserAndAccount(signUpRequest, authorizationHeader);
		} catch (MessagingException | IOException | TemplateException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Errore nell'invio della mail!"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity
					.internalServerError()
					.body(new MessageResponse(e.getMessage()));
		}


		return ResponseEntity.ok(new MessageResponse("Utente registrato con successo!"));
	}


}
