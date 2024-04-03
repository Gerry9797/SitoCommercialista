package com.commercialista.backend.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercialista.backend.enums.StatiAccountEnum;
import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.ERole;
import com.commercialista.backend.models.Role;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;
  
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  PasswordEncoder encoder;

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
	
	//Controlla se l'account associato allo User è stato verificato (mail confermata)
	Account accountUser = accountRepository.findById(user.getId())
		.orElseThrow(() -> new Exception("Nessun account associato a questo utente!"));
	if(accountUser.getStatus().equals(StatiAccountEnum.NON_ATTIVO)) {
		throw new Exception("Email non ancora confermata, controlla la tua posta");
	}
	else if(accountUser.getStatus().equals(StatiAccountEnum.SOSPESO)) {
		throw new Exception("Account sospeso, contattaci se lo ritieni un errore");
	}
	
	Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity
        .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
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
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
