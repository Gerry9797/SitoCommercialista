package com.commercialista.backend.dto.exchange;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.commercialista.backend.exception.GenericException;

/**
 *  */
public class WsProblema implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codice;
	private String stato;
	private String titolo;
	private String descrizione;

	/**
	 * *
	 */
	public WsProblema() {
	}

	/**
	 * * @param codice
	 * 
	 * @param stato       * @param titolo
	 * @param descrizione
	 */
	public WsProblema(String codice, String stato, String titolo, String descrizione) {
		this.codice = codice;
		this.stato = stato;
		this.titolo = titolo;
		this.descrizione = descrizione;
	}

	/**
	 * *
	 */
	public enum Verbosity {
		BASIC(1), MODERATE(2), VERBOSE(3);

		private int level;

		/**
		 * * @param level
		 */
		private Verbosity(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}

		/**
		 * 
		 * @param level
		 * @return
		 */
		public static Verbosity getByLevel(Integer level) {
			if (level == null)
				return Verbosity.MODERATE;
			for (Verbosity value : Verbosity.values()) {
				if (value.getLevel() == level)
					return value;
			}
			return Verbosity.MODERATE;
		}
	}

	/**
	 * * @param ex
	 * 
	 * @return
	 */
	public static WsProblema of(Exception ex) {
		return of(ex, Verbosity.MODERATE);
	}

	/**
	 * * @param ex
	 * 
	 * @param verbosity * @return
	 */
	public static WsProblema of(Exception ex, Integer verbosity) {
		return of(ex, Verbosity.getByLevel(verbosity));
	}

	/**
	 * @param ex
	 * @param verbosity * @return
	 */
	public static WsProblema of(Exception ex, Verbosity verbosity) {
		GenericException exception = null;
		if (ex instanceof GenericException) {
			exception = (GenericException) ex;
		} else {
			exception = new GenericException(500, "ERRORE_GENERICO", "Si e' verificato un errore generico", ex);
		}
		String msgDetail = null;
		if (verbosity == null || Verbosity.BASIC == verbosity) {
			msgDetail = exception.getMessage();
		} else if (Verbosity.MODERATE == verbosity) {
			msgDetail = exception.getMessage();
			Throwable cause = exception.getCause();
			if (cause != null) {
				msgDetail += " -> " + cause.getMessage();
			}
		} else if (Verbosity.VERBOSE == verbosity) {
			msgDetail = stringfy(exception);
		}
		return of(exception.getCode(), exception.getHttpStatus(), msgDetail, msgDetail);
	}

	/**
	 * @param ex * @return
	 */
	public static WsProblema of(GenericException ex) {
		return of(ex.getCode(), ex.getHttpStatus(), ex.getMessage(), ex.getMessage());
	}

	/**
	 * @param ex * @return
	 */
	public static WsProblema of(String codice, int stato, String titolo, String message) {
		WsProblema problema = new WsProblema();
		problema.setCodice(codice);
		problema.setStato("" + stato);
		problema.setTitolo(titolo);
		problema.setDescrizione(message);
		return problema;
	}

	/**
	 * @param error * @return
	 */
	public static String stringfy(WsProblema error) {
		if (error == null) {
			return "";
		}
		List<String> tokens = new ArrayList<>();
		if (!isEmpty(error.getCodice())) {
			tokens.add(error.getCodice().trim());
		}
		if (!isEmpty(error.getStato())) {
			tokens.add(error.getStato().trim());
		}
		if (!isEmpty(error.getTitolo())) {
			tokens.add(error.getTitolo().trim());
		}
		if (!isEmpty(error.getDescrizione())) {
			tokens.add(error.getDescrizione().trim());
		}
		return String.join(" - ", tokens);
	}

	/**
	 * *
	 * 
	 * @param value * @return
	 */
	private static boolean isEmpty(String value) {
		return value == null || value.isEmpty() ? true : false;
	}

	/**
	 * Trasforma in stringa tutto lo stacktrace dell'eccezione * @param exception
	 * 
	 * @return
	 */
	private static String stringfy(Exception exception) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		exception.printStackTrace(ps);
		String output = null;
		try {
			output = os.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}
		return output;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WsProblema [codice=");
		builder.append(codice);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", titolo=");
		builder.append(titolo);
		builder.append(", descrizione=");
		builder.append(descrizione);
		builder.append("]");
		return builder.toString();
	}
}
