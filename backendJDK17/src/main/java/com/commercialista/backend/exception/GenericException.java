package com.commercialista.backend.exception;

public class GenericException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer httpStatus;
	private String code;

	/**
	 * *
	 */
	public GenericException() {
		super();
	}

	/**
	 * * @param httpStatus
	 * 
	 * @param code * @param message
	 */
	public GenericException(int httpStatus, String code, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.code = code;
	}

	/**
	 * * @param httpStatus
	 * 
	 * @param code
	 * @param message * @param cause
	 */
	public GenericException(int httpStatus, String code, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
		this.code = code;
	}

	/**
	 * * @return
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * * @return
	 */
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericException [httpStatus=");
		builder.append(httpStatus);
		builder.append(", code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
