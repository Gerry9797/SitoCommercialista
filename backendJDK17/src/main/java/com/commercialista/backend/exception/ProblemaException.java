package com.commercialista.backend.exception;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class ProblemaException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1262130513714139440L;
	private final String codice;
    private final int codiceHttp;
    private final String titolo;
    private final String descrizione;

    public ProblemaException(MessageSource messageSource, String codice, int codiceHttp, Locale locale, Object... args) {
        super(messageSource.getMessage("error." + codice + ".descrizione", args, locale));
        this.codice = codice;
        this.codiceHttp = codiceHttp;
        this.titolo = messageSource.getMessage("error." + codice + ".titolo", args, locale);
        this.descrizione = messageSource.getMessage("error." + codice + ".descrizione", args, locale);
    }
    
    public ProblemaException(String codice, int codiceHttp, String titolo, String descrizione) {
        super(descrizione);
        this.codice = codice;
        this.codiceHttp = codiceHttp;
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

//    public ProblemaException(String codice, int codiceHttp, Object... args) {
////        this(SpringContext.getBean(MessageSource.class), codice, codiceHttp, SpringContext.getBean(DatiSessioneUtenteService.class).getUserLocale(), args);
//    }

    public String getCodice() {
        return codice;
    }

    public int getCodiceHttp() {
        return codiceHttp;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
