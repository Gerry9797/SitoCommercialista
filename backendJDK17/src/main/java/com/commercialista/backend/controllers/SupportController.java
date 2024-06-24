package com.commercialista.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercialista.backend.payload.request.MessageRequest;
import com.commercialista.backend.services.SupportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/support")
@Tag(name = "Supporto")
public class SupportController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SupportController.class);
	
	@Autowired
	SupportService supportService;
	
    @PostMapping(value = "/send-message", consumes = {"application/json; charset: 'utf-8'"}, produces = {"application/json; charset: 'utf-8'"})
    @Operation(summary = "Invia un messaggio al supporto",
    description = "Invia un messaggio tramite email al supporto")
    public Map<String, Boolean> sendMessageToSupport(
    		@Valid @RequestBody MessageRequest messageRequest
    		) throws Exception {
    	
    	LOGGER.debug("Start {}.{}", "SupportController", "sendMessageToSupport");
    	
    	supportService.sendMessageToSupport(messageRequest);
    	
    	Map<String, Boolean> response = new HashMap<>();
        response.put("sent", Boolean.TRUE);
        return response;
    }

}
