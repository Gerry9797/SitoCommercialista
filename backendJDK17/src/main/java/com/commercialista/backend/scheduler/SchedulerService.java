package com.commercialista.backend.scheduler;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.commercialista.backend.repository.AccountRepository;
import com.commercialista.backend.repository.RoleRepository;
import com.commercialista.backend.repository.UserRepository;
import com.commercialista.backend.services.UserService;

import jakarta.transaction.Transactional;

@Service("schedulerService")
public class SchedulerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);

	@Value("${custom.scheduler.remove-user-with-expired-confirmation.enabled}")
	private boolean enableJobRemoveUserWithExpiredConfirmation;
	
	@Value("${custom.scheduler.remove-user-with-expired-confirmation.maxNumDaysForConfirmation}")
	private int maxNumDaysForConfirmation;
	
	@Autowired
	private UserService userService;
	

//	@Scheduled(fixedRate = 1000) //ogni secondo
//	public void scheduleFixedRateTask() {
//	    System.out.println(
//	      "Fixed rate task - " + System.currentTimeMillis() / 1000);
//	}

//	@Scheduled(fixedRate = 10000) //ogni secondo
//	public void scheduleFixedRateTask() {
//	  log.info("prova info");
//	  log.debug("prova debug");
//	  log.warn("prova warning");
//	  log.error("prova error");
//	}

	// Fires at 3:00 AM every day:
	@Scheduled(cron = "${custom.scheduler.remove-user-with-expired-confirmation.cron}", zone = "Europe/Rome")
	@Transactional
	@Async("scheduler")
	public void ScheduledRemoveAnnunciNonConfermatiEntroNGiorni() {
		if(enableJobRemoveUserWithExpiredConfirmation) {
			System.out.println("Avviato task di rimozione degli account non confermati entro 6 giorni dalla registrazione");
			int accountRimossi = userService.removeAccountNonConfermatiDaNDays(maxNumDaysForConfirmation);
			
		    System.out.println(MessageFormat.format("Sono stati rimossi {0} account", accountRimossi));
		    System.out.println(MessageFormat.format("Terminato task di rimozione degli account non confermati entro {0} giorni dalla registrazione", maxNumDaysForConfirmation));
		}
	}

	// Fires at 3:00 AM every day:
//	@Scheduled(cron = "0 0 3 * * ?", zone = "Europe/Rome")
//	@Transactional
//	public void ScheduledRemoveAnnunciNonAttiviDa30Giorni() {
//		if(BusinessConstants.removeAnnunciInattivi) {
//		    System.out.println("Avviato task di rimozione degli annunci non attivi da 30 giorni dall'inserimento");
//		    int annunciRimossi = annuncioRepository.removeAnnunciInattiviDa30gg();
//		    System.out.println(MessageFormat.format("Sono stati rimossi {0} annunci", annunciRimossi));
//		    System.out.println("Terminato task di rimozione degli annunci non attivi da 30 giorni dall'inserimento");
//		}
//	}

	// Ogni ora
//	@Scheduled(fixedRate = 60*60*1000)
//	@Transactional
//	public void scheduleRemoveExpiredStripeSessions() {
//	    System.out.println("Avviato task di rimozione delle sessioni stripe scadute");
//	    int sessioniRimosse = ordineStripeSessionRepository.removeSessioniExpired();
//	    System.out.println(MessageFormat.format("Sono stati rimossi {0} annunci", sessioniRimosse));
//	    System.out.println("Terminato task di rimozione delle sessioni stripe scadute");
//	    
//	}

	// eseguito ad ogni inizio ora e metà ora (es. 8:00, 8:30, 9:00, 9:30...)
//	@Scheduled(cron = "0 0,30 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?", zone = "Europe/Rome")
//	@Transactional
//	public void scheduleEffettuaRisaliteInTesta() {
//	    System.out.println("Avviato task di attuazione delle risalite");
//	    int risalite = gestioneOrdinamentoAnnunciService.effettuaRisaliteInTesta();
//	    System.out.println(MessageFormat.format("Sono state effettuate {0} risalite", risalite));
//	    System.out.println("Terminato task di attuazione delle risalite");
//	    
//	}

}
