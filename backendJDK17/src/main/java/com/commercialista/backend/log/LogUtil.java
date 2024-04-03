package com.commercialista.backend.log;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class LogUtil {
	public static final String id_transazione = "id_transazione";
	public static final String id_sessione = "id_sessione";
	public static final String cf_utente = "cf_utente";
	public static final String client_ip = "client_ip";
	public static final String web_method = "web_method";
	public static final String request_uri = "request_uri";
	public static final String query_string = "query_string";
	public static final String do_not_log = "DONOTLOG";

	public static HttpServletRequest getHttpServletRequest() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();

			if (servletRequestAttributes != null)
				return servletRequestAttributes.getRequest();
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getIdTransazione() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.id_transazione);
		else
			return null;
	}

	public static String getIdSessione() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.id_sessione);
		else
			return null;
	}

	public static String getCfUtente() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.cf_utente);
		else
			return null;
	}

	public static String getRequestUri() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.request_uri);
		else
			return null;
	}

	public static String getQueryString() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.query_string);
		else
			return null;
	}

	public static String getClientIp() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.client_ip);
		else
			return null;
	}
	
	public static String getWebMethod() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.web_method);
		else
			return null;
	}

	public static String getDoNotLog() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null)
			return request.getHeader(LogUtil.do_not_log);
		else
			return null;
	}
}
