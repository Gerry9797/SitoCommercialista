package com.commercialista.backend.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.commercialista.backend.payload.request.MessageRequest;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Service
public class SupportService {
	
	@Autowired
	private EmailSenderService emailSenderService;
	
    @Value("${custom.email.support-email}")
    private String supportEmail;
    
    @Value("${custom.email.support-send-message-panel-subject}")
    private String messageSubject;

	public void sendMessageToSupport(@Valid MessageRequest messageRequest) throws MessagingException, UnsupportedEncodingException {
		
		emailSenderService.sendEmail(
				supportEmail,
				messageRequest.getEmail(),
				messageSubject,
				buildMessageToSend(messageRequest));
	}
	
	private String buildMessageToSend(MessageRequest messageRequest) {
		return String.format(
					"Mittente: {} {}\n"
					+ "Telefono: {}\n"
					+ "Messaggio:\n{}",
					messageRequest.getNome(),
					messageRequest.getCognome(),
					messageRequest.getTelefono(),
					messageRequest.getMessage()
				);
	}

}
