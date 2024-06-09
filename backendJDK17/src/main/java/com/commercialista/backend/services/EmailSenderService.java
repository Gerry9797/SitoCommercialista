package com.commercialista.backend.services;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.commercialista.backend.models.Account;
import com.commercialista.backend.models.User;
import com.commercialista.backend.utils.DateManager;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service("emailSenderService")
public class EmailSenderService {
	
	private JavaMailSender javaMailSender;
	
    private Configuration freemarkerConfig;
    
    @Value("${custom.server.frontend.base-url}")
    private String FEBaseUrl;
    
    @Value("${custom.email.base-url-server-for-content}")
    private String BEBaseUrlForContent;
    
    @Value("${custom.email.no-response-email}")
    private String noResponseEmail;
    
    @Value("${custom.email.support-email}")
    private String supportEmail;
    
    @Value("${custom.email.content.logo-in-email}")
    private String logoInEmailRelativePath;
    
    @Value("${custom.email.content.bg-banner}")
    private String bgBannerRelativePath;
    
    @Value("${custom.email.content.bollino-sito}")
    private String bollinoSitoRelativePath;
    
    @Value("${custom.email.content.info-icon}")
    private String infoIconRelativePath;
    
    @Value("${custom.site.name}")
    private String siteName;
    
    @Value("${custom.site.domain}")
    private String siteDomain;
    

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }
    
    @Async
    public void sendEmail(String receiver, String sender, String subject, String body) throws MessagingException, UnsupportedEncodingException {
    	MimeMessage message = javaMailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    	helper.setTo(receiver);
    	helper.setFrom(new InternetAddress(noResponseEmail, siteName));
    	helper.setSubject(subject);
    	helper.setReplyTo(sender);
    	helper.setText(body, true);
    	javaMailSender.send(message);
    }
    
    //riempie il modello da passare a freemarker con coppie chiave valore comuni in ogni email (es. logo, link sito...)
    private void fillModelWithCommonValues(Map<String, Object> model) {
    	model.put("logo_src", BEBaseUrlForContent + logoInEmailRelativePath);
    	model.put("api_url", FEBaseUrl);
    	model.put("sito_href", siteDomain);
    	model.put("sito_href_full", FEBaseUrl);
        model.put("nome_sito", siteName);
        model.put("support_email", supportEmail);
        model.put("no_response_email", noResponseEmail);
        model.put("bollino_sito", BEBaseUrlForContent + bollinoSitoRelativePath);
        model.put("bg_banner", BEBaseUrlForContent + bgBannerRelativePath);
        model.put("info_icon", BEBaseUrlForContent + infoIconRelativePath);
        model.put("pannelloGestisciAnnunciFE", FEBaseUrl + "/prova");
        }
    
    @Async
    public void sendEmailConfermaRegistrazione(Account account, User user) throws MessagingException, IOException, TemplateException {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setFrom(new InternetAddress(noResponseEmail, siteName));
        helper.setSubject("Conferma la tua registrazione: " + user.getEmail());
        String emailContent = getEmailContent_ConfermaRegistrazione(account, user);
        helper.setText(emailContent, true);
        
    	javaMailSender.send(message);
    }
    
    private String getEmailContent_ConfermaRegistrazione(Account account, User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        fillModelWithCommonValues(model);
        
        //fill params
        model.put("user_email", user.getEmail());
        
        Date expirationDate = DateManager.addDaysToDate(account.getDataPubb(), 6);
        long remainingDays = DateManager.getDayDifference(new Date(), expirationDate);
        model.put("remainingDays", remainingDays);
        
        String verification_url = FEBaseUrl + "/account/" + account.getIdUser() + "/conferma/" + account.getVerificationCode();
        model.put("verification_url", verification_url);

        freemarkerConfig.getTemplate("template_conferma_registrazione.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    
    @Async
    public void sendEmailConfermaCambioEmail(Account account, User user, String newEmail) throws MessagingException, IOException, TemplateException {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(newEmail);
        helper.setFrom(new InternetAddress(noResponseEmail, siteName));
        helper.setSubject("Coferma cambio email: " + newEmail);
        String emailContent = getEmailContent_ConfermaCambioEmail(account, user, newEmail);
        helper.setText(emailContent, true);
        
    	javaMailSender.send(message);
    }
    
    private String getEmailContent_ConfermaCambioEmail(Account account, User user, String newEmail) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        fillModelWithCommonValues(model);
        
        
    	 model.put("CONTENT_TITLE",  "Richiesta cambio email al tuo account");
         model.put("CONTENT_HELLO",  "Ciao");
         model.put("CONTENT_DESC1",  "dal tuo account su");
         model.put("CONTENT_DESC2",  "è stata fatta una richiesta di cambio email.");
         model.put("CONTENT_DESC3",  "Se sei stato tu e vuoi confermare");
         model.put("CONTENT_DESC4",  "come email del tuo account sul nostro sito clicca sul pulsante sottostante di conferma.");
         model.put("CONTENT_CLICK",  "Clicca sul pulsante per confermare la tua email.");
         model.put("CONTENT_BUTTON",  "CONFERMA CAMBIO EMAIL");
         model.put("CONTENT_NEEDS",  "Hai bisogno di competenze digitali e/o di sviluppo software?");
         model.put("CONTENT_CONTACT",  "Contattaci");
         model.put("CONTENT_BE_TOP",  "per essere sempre al Top!");
         model.put("CONTENT_NO_RESP",  "Questa email è stata inviata automaticamente.");
         model.put("CONTENT_NO_RESP2",  "Non rispondere a questo messaggio. ");
         model.put("CONTENT_NO_RESP3",  "Ti serve aiuto? Contattaci all’indirizzo email");
         model.put("CONTENT_TERMS",  "Termini e condizioni");
         model.put("CONTENT_PRIVACY",  "Informativa sulla privacy");
         model.put("CONTENT_CONTACTS",  "Contatti");
        
        //fill params
        model.put("nuova_email", newEmail);
        
        String cambioMailUrl = FEBaseUrl + "/users/" + account.getIdUser() + "/newEmail/" + newEmail + "/" + account.getVerificationCode();
        model.put("conferma_cambio_email_url", cambioMailUrl);
        freemarkerConfig.getTemplate("template_conferma_cambio_email.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @Async
	public void sendEmailRecuperoPassword(Account account, User user) throws MessagingException, IOException, TemplateException {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setFrom(new InternetAddress(noResponseEmail, siteName));
        helper.setSubject("Recupero Password per: " + user.getEmail());
        String emailContent = getEmailContent_RecuperoPassword(account, user);
        helper.setText(emailContent, true);
        
    	javaMailSender.send(message);
	}

	private String getEmailContent_RecuperoPassword(Account account, User user) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        fillModelWithCommonValues(model);
        
      
    	 model.put("CONTENT_TITLE",  "Hai dimenticato la tua password?");
    	 model.put("CONTENT_CONFIRM",  "Nessun problema, ti aiuteremo a impostarne una nuova!");
    	 model.put("CONTENT_HELLO",  "Ciao");
         model.put("CONTENT_DESC1",  "se hai dimenticato la password del tuo account su");
         model.put("CONTENT_DESC2",  "non preoccuparti, ci pensiamo noi");
         model.put("CONTENT_CLICK",  "Clicca sul pulsante che vedi qui in basso per reimpostarla.");
         model.put("CONTENT_BUTTON",  "REIMPOSTA PASSWORD");
         model.put("CONTENT_NEEDS",  "Hai bisogno di competenze digitali e/o di sviluppo software?");
         model.put("CONTENT_CONTACT",  "Contattaci");
         model.put("CONTENT_BE_TOP",  "per essere sempre al Top!");
         model.put("CONTENT_NO_RESP",  "Questa email è stata inviata automaticamente.");
         model.put("CONTENT_NO_RESP2",  "Non rispondere a questo messaggio. ");
         model.put("CONTENT_NO_RESP3",  "Ti serve aiuto? Contattaci all’indirizzo email");
         model.put("CONTENT_TERMS",  "Termini e condizioni");
         model.put("CONTENT_PRIVACY",  "Informativa sulla privacy");
         model.put("CONTENT_CONTACTS",  "Contatti");
        
        
        //fill params
        model.put("user_email", user.getEmail());
        
        String verification_url = FEBaseUrl + "/users/" + account.getIdUser() + "/resetPassword/" + account.getVerificationCode();
        model.put("verification_url", verification_url);

        freemarkerConfig.getTemplate("template_recupero_password.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
	}
}
