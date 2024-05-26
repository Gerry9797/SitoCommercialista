package com.commercialista.backend.utils;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.commercialista.backend.security.services.UserDetailsImpl;

@Service
public class LoggedUserManager {
	
	public UserDetailsImpl getLoggedUserDetails(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		UsernamePasswordAuthenticationToken userDetWrapper = (UsernamePasswordAuthenticationToken) principal;
		UserDetailsImpl userDet = (UserDetailsImpl) userDetWrapper.getPrincipal();
		return userDet;
	}

}
