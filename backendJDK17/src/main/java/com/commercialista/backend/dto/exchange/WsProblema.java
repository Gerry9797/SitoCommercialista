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
	private String code;
	private String status;
	private String title;
	private String message;

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
	public WsProblema(String code, String status, String title, String message) {
		this.code = code;
		this.status = status;
		this.title = title;
		this.message = message;
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
	public static WsProblema of(String code, int status, String title, String message) {
		WsProblema problema = new WsProblema();
		problema.setCode(code);
		problema.setStatus("" + status);
		problema.setTitle(title);
		problema.setMessage(message);
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
		if (!isEmpty(error.getCode())) {
			tokens.add(error.getCode().trim());
		}
		if (!isEmpty(error.getStatus())) {
			tokens.add(error.getStatus().trim());
		}
		if (!isEmpty(error.getTitle())) {
			tokens.add(error.getTitle().trim());
		}
		if (!isEmpty(error.getMessage())) {
			tokens.add(error.getMessage().trim());
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WsProblema [code=");
		builder.append(code);
		builder.append(", status=");
		builder.append(status);
		builder.append(", title=");
		builder.append(title);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
