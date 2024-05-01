package com.commercialista.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProblemaGenericoException extends ProblemaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6248462167523512248L;
	private static final Logger log = LoggerFactory.getLogger(ProblemaGenericoException.class);

	public ProblemaGenericoException(Exception e) {
		super("0000", 500, e.getClass().getSimpleName(), e.getMessage());
		log.error("ProblemaGenericoException: ", e);
	}

}
