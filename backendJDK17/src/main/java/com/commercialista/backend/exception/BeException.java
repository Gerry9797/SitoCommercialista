package com.commercialista.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class BeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final Code code;
    private final Object[] parameters;

    public BeException(Code code) {
    	super(code.getMessageTemplate()!=null?code.getMessageTemplate():"");
    	this.code = code;
    	this.parameters = new Object[0];
    }
    
    public BeException(Code code, Object... parameters) {
    	super(String.format(code.getMessageTemplate()!=null?code.getMessageTemplate():"", parameters));
        this.code = code;
        this.parameters = parameters;
    }

	public BeException(List<FieldError> fieldErrors) {
		this(Code.BAD_REQUEST, fieldErrors.stream()
				.map(fieldError -> String.format("%s.%s %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.joining(". ", "", "."))
		);
	}

    public HttpStatus getHttpStatus() {
        return code.getHttpStatus();
    }

	public String getMessaggioErrore() {
		if (this.parameters != null && this.parameters.length > 0)
			return String.format(code.getMessageTemplate(), parameters);
		else
			return code.getMessageTemplate();
	}

    public String getCodiceErrore() {
        return code.getErrorCode();
    }

    public enum Code {
    	CUSTOM_ERROR("CUSTOM_ERROR", "Si è verificato un errore: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		GENERIC_ERROR("GENERIC_ERROR", "Si è verificato un errore generico", HttpStatus.INTERNAL_SERVER_ERROR),
		ACTION_NOT_PERMISSION("ACTION_NOT_PERMISSION", "Azione non consentita", HttpStatus.INTERNAL_SERVER_ERROR),
		ACTION_NOT_FOUND("ACTION_NOT_FOUND", "Azione %s non trovata tra quelle collegate alla classe atto con id: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		ALREADY_EXISTS("ALREADY_EXISTS", "La risorsa %s di tipo %s già esiste", HttpStatus.CONFLICT),
		BAD_FILE_NAME("BAD_FILE_NAME", "Il nome del file non è in un formato valido", HttpStatus.BAD_REQUEST),
		BAD_FILE_TYPE("BAD_FILE_TYPE", "Il tipo di file inviato non è tra quelli consentiti", HttpStatus.NOT_ACCEPTABLE),
		BAD_JSON("BAD_JSON", "il jsonValue non è valido", HttpStatus.INTERNAL_SERVER_ERROR),
		BAD_JSON_CONVERSION("BAD_JSON_CONVERSION", "Errore durante la conversione dell'oggetto json: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		BAD_PARAM("BAD_PARAM", "il parametro %s non è valido", HttpStatus.BAD_REQUEST),
		BAD_REQUEST("BAD_REQUEST", "La request inserita non è valida. %s", HttpStatus.BAD_REQUEST),
		BAD_RESPONSE("BAD_RESPONSE", "Risposta del server non conforme alle attese", HttpStatus.INTERNAL_SERVER_ERROR),
		BAD_TABLE_NAME("BAD_TABLE_NAME", "nome tabella %s non valido", HttpStatus.BAD_REQUEST),
		CLASSE_ATTO_NOT_FOUND("CLASSE_ATTO_NOT_FOUND", "idClasseAtto non impostato nel documento %s", HttpStatus.INTERNAL_SERVER_ERROR),
		CLASSE_ATTO_UNSET("CLASSE_ATTO_UNSET", "Classe atto con id: %s non trovata tra quelle configurate nello userTask: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		COMUNICATION_ERROR("COMUNICATION_ERROR", "Errore di comunicazione (url: %s, response: %s)", HttpStatus.BAD_GATEWAY),
		CONFLICT("CONFLICT", "valore atteso: %s , invece e': %s", HttpStatus.CONFLICT),
		CONFLICT_ALLEGATI("CONFLICT_ALLEGATI", "caricamento obbligatorio degli allegati", HttpStatus.CONFLICT),
		CONFLICT_CLASSE_ATTO("CONFLICT_CLASSE_ATTO", "caricamento obbligatorio del documento", HttpStatus.CONFLICT),
		CONFLICT_SEZIONI_DOCUMENTO("CONFLICT_SEZIONI_DOCUMENTO", "modifica obbligatoria del testo della proposta", HttpStatus.CONFLICT),
		CONFLICT_SEZIONE_VUOTA("CONFLICT_SEZIONE_VUOTA", "modifica obbligatoria del testo della proposta", HttpStatus.CONFLICT),
		CONFLICT_ITER_PUBBLICATO("ITER_P_CONFLICT", "Esistono più iter di classe %s in stato pubblicato", HttpStatus.CONFLICT),
		CONFLICT_WS_ESTERNO("WS_ESTERNO_CONFLICT", "Esistono più wsEsterni con id %s", HttpStatus.CONFLICT),
		CONFLICT_PROCEDIMENTO_PUBBLICATO("PROCEDIMENTO_P_CONFLICT", "Esistono più procedimenti con idTipoProcedimento %s in stato pubblicato", HttpStatus.CONFLICT),
		DOC_ESISTENTE_FASCICOLO_ERROR("DOC_ESISTENTE_FASCICOLO_ERROR", "Il documento è già presente nel fascicolo", HttpStatus.CONFLICT),
		ESITO_NON_CONFORME("ESITO_NON_CONFORME", "esito inserito non conforme a quello previsto", HttpStatus.BAD_REQUEST),
		ESITO_MANCANTE("ESITO_MANCANTE", "errore: valori di esito non valorizzati in fase di creazione", HttpStatus.CONFLICT),
		FILE_CANT_CREATE("FILE_CANT_CREATE", "Problemi durante la creazione del file; nome file: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		FILE_CANT_DELETE("FILE_CANT_DELETE", "Problemi durante la cancellazione del file; nome file: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		FILE_ERROR("FILE_ERROR", "Problemi durante la generazione del file; nome file: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		FILE_NOT_FOUND("FILE_NOT_FOUND", "File %s non trovato", HttpStatus.NOT_FOUND),
		FILE_TOO_LONG("FILE_TOO_LONG", "La dimensione del file inviato supera i limiti consentiti (%s > %s)", HttpStatus.BAD_REQUEST),
		FILE_TOO_LONG_PER_ALLEGATO("FILE_TOO_LONG_PER_ALLEGATO", "La dimensione dei file caricati per tipo allegato supera i limiti consentiti (%s > %s)", HttpStatus.BAD_REQUEST),
		NUMBER_FILE_TOO_LONG_PER_ALLEGATO("NUMBER_FILE_TOO_LONG_PER_ALLEGATO", "Il numero dei file caricati per tipo allegato supera i limiti consentiti (%s > %s)", HttpStatus.BAD_REQUEST),
		INSERT_DOC_FASCICOLO_ERROR("INSERT_DOC_FASCICOLO_ERROR", "Errore nell'inserimento del documento %s nel fascicolo %s: %s)", HttpStatus.INTERNAL_SERVER_ERROR),
		IO_ERROR("IO_ERROR", "Problemi durante la lettura del file; nome file: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		MANDATORY("MANDATORY", "errore: il valore del campo obbligatorio %s è mancante", HttpStatus.BAD_REQUEST),
		MAIL_ERROR("SEND_MAIL_ERROR", "errore ell'invio della mail", HttpStatus.BAD_GATEWAY),
		MANAGED_ERROR("MANAGED_ERROR", "Il servizio %s ha risposto come segue: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		MULTI_CONFLICT("MULTI_CONFLICT", "Ci sono più istanze della risorsa %s di tipo %s", HttpStatus.CONFLICT),
		MUST_EXISTS("MUST_EXISTS", "La risorsa di tipo %s deve esistere", HttpStatus.NOT_FOUND),
		NOTIFICA_ERROR("NOTIFICA_ERROR", "Errore durante la creazione della notifica", HttpStatus.UNPROCESSABLE_ENTITY),
		NOT_FINDABLE("NOT_FINDABLE", "Non ci sono risorse per %s", HttpStatus.NOT_FOUND),
		NOT_FINDABLE_UFFICI("UFFICI_MISMATCH", "Ufficio Task %s incopatiblie con l'ufficio utente", HttpStatus.NOT_FOUND),
		NOT_FOUND("NOT_FOUND", "La risorsa %s di tipo %s non esiste", HttpStatus.NOT_FOUND),
		NOT_FOUND_ITER_PUBBLICATO("ITER_P_NOT_FOUND", "L'iter di classe %s non esiste in stato pubblicato", HttpStatus.NOT_FOUND),
		NOT_FOUND_PROCEDIMENTO_PUBBLICATO("PROCEDIMENTO_P_NOT_FOUND", "Il procedimento con id tipo procedimento %s non esiste in stato pubblicato", HttpStatus.NOT_FOUND),
		PROTOCOLLAZIONE_ERROR("PROTOCOLLAZIONE_ERROR", "Errore durante la protocollazione: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		PROTOCOLLAZIONE_ASSOCIA_FASCICOLO("PROTOCOLLAZIONE_ASSOCIA_FASCICOLO", "Errore durante l'associazione del documento prot. al fascicolo: %s", HttpStatus.INTERNAL_SERVER_ERROR),
		PROTOCOLLA_DOCUMENTO_ERROR("PROTOCOLLA_DOCUMENTO_ERROR", "Errore nella protocollazione del documento %s: %s", HttpStatus.BAD_REQUEST),

		PROTOCOLLA_RICHIESTA_ERROR("PROTOCOLLA_RICHIESTA_ERROR", "Errore nella protocollazione della richiesta %s: %s", HttpStatus.BAD_REQUEST),
		PROTOCOLLA_ERROR_CAMPO_OBBLIGATORIO("PROTOCOLLA_ERROR_CAMPO_OBBLIGATORIO", "Errore nella richiesta di protocollazione : il campo %s è obbligatorio", HttpStatus.BAD_REQUEST),
		REMOTE_ERROR("REMOTE_ERROR", "Il server remoto ha sollevato l'errore %s", HttpStatus.BAD_GATEWAY),
    	RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "La risorsa %s non è stata trovata", HttpStatus.NOT_FOUND),
    	SAME_NAME("SAME_NAME", "Esiste già un fascicolo con nome %s", HttpStatus.CONFLICT),
    	SAVE_ERROR("SAVE_ERROR", "Errore nel salvataggio di %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	UNAUTHORIZED("UNAUTHORIZED", "Utente non autorizzato", HttpStatus.UNAUTHORIZED),
		EMPTY_TIPO_PROCEDIMENTO("EMPTY_TIPO_PROCEDIMENTO", "Non sono stati trovati tipi procedimento per l'utente corrente", HttpStatus.BAD_REQUEST),
		EMPTY_ATTIVITA("EMPTY_ATTIVITA", "Non sono state trovate attivita per l'utente corrente %s", HttpStatus.BAD_REQUEST),
		AZIONE_NON_TROVATA("AZIONE_NON_TROVATA", "Azione valida non trovata", HttpStatus.BAD_REQUEST),
		USER_MULTI_UFFICI("USER_MULTI_UFFICI", "L'utente non ha più di un ufficio selezionato", HttpStatus.CONFLICT),
    	USER_ZERO_UFFICI("USER_ZERO_UFFICI", "L'utente non ha nessun ufficio selezionato", HttpStatus.INTERNAL_SERVER_ERROR),
    	WS_ESTERNO_ERROR("WS_ESTERNO_ERROR", "Controllare la configurazione del ws esterno idServizioEsterno %s, tipoServizioEsterno %s, nomeServizioEsterno %s: parametri restituiti %s, %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	WS_ESTERNO_BAD_PARAM("WS_ESTERNO_BAD_PARAM", "Parametro mal configurato nel WS ESTERNO: '%s'. Dati parametro: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	UTENTE_NON_ABILITATO("UTENTE_NON_ABILITATO", "L'utente %s non ha le abilitazioni corrette per l'assegnazione del task", HttpStatus.INTERNAL_SERVER_ERROR),
    	NAMING_SEZIONE_DATI_ERROR("NAMING_SEZIONE_DATI_ERROR", "Errore nei naming delle proprieta della sezioneDati %s", HttpStatus.BAD_REQUEST),
    	WORKFLOW_ERROR("WORKFLOW_ERROR", "Errore di workflow: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	ALBO_ERROR("ALBO_ERROR", "Errore del servizio Albo: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	BDU_ERROR("BDU_ERROR", "Errore della BDU: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	FASCICOLO_ERROR("FASCICOLO_ERROR", "Errore di Fascicolo: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	ALLEGATO_NON_CONFORME("ALLEGATO_NON_CONFORME", "Il flag parte integrante e il flag pubblicabile dell'allegato '%s' non sono settati correttamente", HttpStatus.BAD_REQUEST),
    	ALLEGATO_AZIONE_OBBLIGATORIA("ALLEGATO_AZIONE_OBBLIGATORIA", "Prevista azione obbligatoria su allegato", HttpStatus.BAD_REQUEST),
    	FIRMA_OBBLIGATORIA("FIRMA_OBBLIGATORIA", "Il documento %s deve essere firmato", HttpStatus.INTERNAL_SERVER_ERROR),
    	TASK_COMPLETION_IN_PROGRESS("TASK_COMPLETION_IN_PROGRESS", "L'attività %s è già in fase di completamento. Si prega di attendere", HttpStatus.CONFLICT),
		WS_ESTERNO_NOT_FOUND("WS_ESTERNO_NOT_FOUND", "WS Esterno non trovato",HttpStatus.NOT_FOUND),
		RECUPERO_CERTIFICATI_ERROR("RECUPERO_CERTIFICATI_ERROR","Errore durante il recupero dei certificati" , HttpStatus.INTERNAL_SERVER_ERROR ),
		FIRMA_DOCUMENTI_ERROR("FIRMA_DOCUMENTI_ERROR", "Errore durante la firma dei documenti: %s", HttpStatus.INTERNAL_SERVER_ERROR ),
		GENERAZIONE_OTP_ERROR("GENERAZIONE_OTP_ERROR", "Errore nella generazione del token otp: %s", HttpStatus.INTERNAL_SERVER_ERROR ),
    	TASK_COMPLETION_ERROR("TASK_COMPLETION_ERROR", "Errore nel completamento del task: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    	INVIA_TIPO_COMUNICAZIONE_ERROR_CAMPO_OBBLIGATORIO("INVIA_TIPO_COMUNICAZIONE_ERROR_CAMPO_OBBLIGATORIO", "Errore nella richiesta : il campo %s è obbligatorio", HttpStatus.BAD_REQUEST);

		private final String errorCode;
        private final String messageTemplate;
        private final HttpStatus httpStatus;

        Code(String errorCode, String messageTemplate, HttpStatus httpStatus) {
            this.errorCode = errorCode;
            this.messageTemplate = messageTemplate;
            this.httpStatus = httpStatus;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getMessageTemplate() {
            return messageTemplate;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }
}
