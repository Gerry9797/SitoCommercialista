package com.commercialista.backend.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercialista.backend.enums.StatiAccountEnum;
import com.commercialista.backend.exception.ResourceNotFoundException;
import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.User;
import com.commercialista.backend.payload.request.ChangePasswordRequest;
import com.commercialista.backend.repository.AccountRepository;
import com.commercialista.backend.repository.UserRepository;
import com.commercialista.backend.security.services.UserDetailsImpl;
import com.commercialista.backend.services.EmailSenderService;
import com.commercialista.backend.services.UserService;
import com.commercialista.backend.utils.LoggedUserManager;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/user")
public class AccountController {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoggedUserManager loggedUserManager;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@GetMapping("/account/{userId}/details")
	public ResponseEntity<Account> getAccountDetails(HttpServletRequest request, @PathVariable(value = "userId") Long userId) throws Exception{
		
		UserDetailsImpl userDet = loggedUserManager.getLoggedUserDetails(request);
		Long loggedUserId = userDet.getId();
		if(loggedUserId != userId) {
			throw new Exception("Permission Denied! Your account is not that!");
		}
		Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
	
		//remove verification code from response
		account.setVerificationCode(null);
		return ResponseEntity.ok().body(account);
	}
    
	@PutMapping("/account/{userId}/confirm/{verificationCode}")
    public ResponseEntity<User> verifyAccountById(@PathVariable(value = "userId") Long userId, @PathVariable(value = "verificationCode") String verificationCode)
        throws ResourceNotFoundException, IOException, TemplateException, MessagingException {
  
    	Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
    	if(! account.getVerificationCode().equals(verificationCode)) throw new ResourceNotFoundException("Verification code sbagliato per questo ID : " + userId);
  
    	if(account.getStatus().equals(StatiAccountEnum.NON_ATTIVO)) { //se è la prima volta che si arriva qui allora viene effettuata la verifica dell'email e confermata la registrazione
    		account = userService.confirmRegistration(account);
    	}
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User non trovato per questo ID : " + userId));
		return ResponseEntity.ok().body(user);
    	
    }
	
	@PutMapping("/account/{userId}/changeEmailRequest")
	public ResponseEntity<Boolean> requestChangeEmail(
			HttpServletRequest request,
			@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody String newEmail
			) throws Exception{
		
		UserDetailsImpl userDet = loggedUserManager.getLoggedUserDetails(request);
		Long loggedUserId = userDet.getId();
		if(loggedUserId != userId) {
			throw new Exception("Permesso Negato! Questo non è il tuo account!");
		}
		User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Utente non trovato per questo ID : " + userId));
    	Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
    	//controlla se esiste già questa email
    	Optional<User> userWithNewEmail = userRepository.findByEmail(newEmail);
    	if(userWithNewEmail.isPresent()) {
    		if(userWithNewEmail.get().getId().equals(userId)) {
    			throw new Exception("E' già la tua email");
    		}
    		else {
    			throw new Exception("Email già associata ad un altro account");
    		}
    		
    	}
		//send email with confirmation link to change user email
		emailSenderService.sendEmailConfermaCambioEmail(account, user, newEmail);
		
		return ResponseEntity.ok().body(true);
	}
	
	
	@PutMapping("/account/{userId}/newEmail/{newEmail}/{verificationCode}")
    public ResponseEntity<User> changeEmail(@PathVariable(value = "userId") Long userId, @PathVariable(value = "verificationCode") String verificationCode, @PathVariable(value = "newEmail") String newEmail)
        throws Exception {
  
    	Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
    	if(! account.getVerificationCode().equals(verificationCode)) throw new ResourceNotFoundException("Verification code sbagliato per questo ID : " + userId);
  
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("User non trovato per questo ID : " + userId));
    	
    	//controlla se esiste già questa email
    	Optional<User> userWithNewEmail = userRepository.findByEmail(newEmail);
    	if(userWithNewEmail.isPresent() && !userWithNewEmail.get().getId().equals(userId)) {
    		throw new Exception("Email già associata ad un altro account");
    	}
    	
    	user.setEmail(newEmail);
    	user.setUsername(newEmail.split("@")[0]);
    	userRepository.save(user);
		return ResponseEntity.ok().body(user);
    	
    }
	
	@PutMapping("/account/{userId}/newPassword")
    public ResponseEntity<User> changePassword(
    		@PathVariable(value = "userId") Long userId, 
    		@Valid @RequestBody ChangePasswordRequest request)
    		throws Exception {
  
    	Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
  
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Utente non trovato per questo ID : " + userId));
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	boolean isPasswordCorrect = encoder.matches(request.getOldPassword(), user.getPassword());
    	
    	if(!isPasswordCorrect) {
    		throw new Exception("La vecchia password è errata!");
    	}
    	
    	user.setPassword(encoder.encode(request.getNewPassword()) );
    	userRepository.save(user);
		return ResponseEntity.ok().body(user);
    	
    }
	
	@PutMapping("/account/{email}/sendMailToRecoverPassword")
	public ResponseEntity<Boolean> sendMailToRecoverPassword(
			@PathVariable(value = "email") String email) throws Exception{
		
		User user = userRepository.findByEmail(email)
    			.orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con questa EMAIL : " + email));
    	Account account = accountRepository.findById(user.getId())
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + user.getId()));
    	if(!account.getStatus().equals(StatiAccountEnum.NON_ATTIVO)) {
    		throw new Exception("L'account non è attivo!");
    	}
    	
		//send email with confirmation link to change user email
		emailSenderService.sendEmailRecuperoPassword(account, user);
		
		return ResponseEntity.ok().body(true);
	}
	
	@PutMapping("/account/{userId}/resetPassword")
    public ResponseEntity<Boolean> resetPassword(
    		@PathVariable(value = "userId") Long userId,
    		@Valid @RequestBody ChangePasswordRequest request
    		) throws Exception {
  
    	Account account = accountRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Account non trovato per questo ID : " + userId));
    	if(! account.getVerificationCode().equals(request.getVerificationCode())) throw new ResourceNotFoundException("Verification code sbagliato per questo ID : " + userId);
    	
    	User user = userRepository.findById(userId)
    			.orElseThrow(() -> new ResourceNotFoundException("Utente non trovato per questo ID : " + userId));
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	
    	user.setPassword(encoder.encode(request.getNewPassword()) );
    	userRepository.save(user);
		return ResponseEntity.ok().body(true);
    	
    }
}
