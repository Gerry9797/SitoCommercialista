package com.commercialista.backend.beans;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.commercialista.backend.log.LogUtil;
import com.commercialista.backend.utils.CachedBodyHttpServletRequest;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceExecutionTimeFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExecutionTimeFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long start = new Date().getTime();

		CachedBodyHttpServletRequest  req = new CachedBodyHttpServletRequest ((HttpServletRequest) request);
		ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper((HttpServletResponse) response);

		boolean flStartTransazione = setInfoLog(req);
		try {

			if (flStartTransazione)
				LOGGER.info("START transazione");

			logRequest(req);

			chain.doFilter(req, resp);

			logResponse(resp);

			resp.copyBodyToResponse();

		} catch (Exception e) {
			throw e;
		} finally {
			long end = new Date().getTime();
			long app = end - start;
			if (app > 2000)
				LOGGER.info("ExecutionTimeFilter ms: {}", app);

			if (flStartTransazione)
				LOGGER.info("END transazione");
		}

	}

	private void logResponse(ContentCachingResponseWrapper response) {
		byte[] responseBody = response.getContentAsByteArray();

		String responseBodyString = new String(responseBody, StandardCharsets.UTF_8);
		LOGGER.info("responseBody: {}", responseBodyString);

	}

	private void logRequest(CachedBodyHttpServletRequest  req) throws IOException {
		byte[] requestBody = req.getInputStream().readAllBytes();
		String requestBodyString = new String(requestBody, StandardCharsets.UTF_8);
		
	    // Se il corpo della richiesta è vuoto, leggi il corpo dalla nuova istanza della richiesta HttpServletRequest
//	    if (requestBody.length == 0) {
//	        try {
//	            requestBodyString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//	        } catch (IOException e) {
//	            LOGGER.error("Errore durante la lettura del corpo della richiesta", e);
//	        }
//	    }

		String queryString = StringUtils.isBlank(req.getQueryString()) ? "" : "?" + req.getQueryString();
		// "\u001B[36;4m" rappresenta il colore celestino, mente "\u001B[0m" rappresenta la sottolineatura
		LOGGER.info("url: \u001B[36;4m{}\u001B[0m method: \u001B[36;4m{}\u001B[0m requestBody: {}", req.getRequestURL() + queryString, req.getMethod(),
				requestBodyString);
	}

	private boolean setInfoLog(CachedBodyHttpServletRequest  request) {
		boolean flStartTransazione;
		if (request.getHeader(LogUtil.id_transazione) == null) {
			flStartTransazione = true;
			MDC.put(LogUtil.id_transazione, UUID.randomUUID().toString());
		} else {
			flStartTransazione = false;
			MDC.put(LogUtil.id_transazione, request.getHeader(LogUtil.id_transazione));
		}

		if (request.getHeader(LogUtil.id_sessione) == null)
			MDC.put(LogUtil.id_sessione, request.getSession().getId());
		else
			MDC.put(LogUtil.id_sessione, request.getHeader(LogUtil.id_sessione));

//		if (request.getHeader(LogUtil.cf_utente) == null) {
//			DatiSessioneUtente datiSessioneUtente = (DatiSessioneUtente) request.getSession()
//					.getAttribute(SessioneUtenteUtil.DATI_SESSIONE_UTENTE);
//
//			if (datiSessioneUtente != null && datiSessioneUtente.getUtenteLoggato() != null)
//				MDC.put(LogUtil.cf_utente, datiSessioneUtente.getUtenteLoggato().getCodiceFiscale());
//			else
//				MDC.remove(LogUtil.cf_utente);
//		} else
//			MDC.put(LogUtil.cf_utente, request.getHeader(LogUtil.cf_utente));

		if (request.getHeader(LogUtil.request_uri) == null)
			MDC.put(LogUtil.request_uri, request.getRequestURI());
		else
			MDC.put(LogUtil.request_uri, request.getHeader(LogUtil.request_uri));

		if (request.getHeader(LogUtil.query_string) == null)
			MDC.put(LogUtil.query_string, request.getQueryString());
		else
			MDC.put(LogUtil.query_string, request.getHeader(LogUtil.query_string));

		if (request.getHeader(LogUtil.client_ip) == null)
			MDC.put(LogUtil.client_ip, request.getHeader("X-Forwarded-For"));
		else
			MDC.put(LogUtil.client_ip, request.getHeader(LogUtil.client_ip));

		if (request.getHeader(LogUtil.web_method) == null)
			MDC.put(LogUtil.web_method, request.getMethod());
		else
			MDC.put(LogUtil.web_method, request.getHeader(LogUtil.web_method));
		
		if (request.getHeader(LogUtil.do_not_log) == null)
			MDC.remove(LogUtil.do_not_log);
		else
			MDC.put(LogUtil.do_not_log, request.getHeader(LogUtil.do_not_log));

		return flStartTransazione;

	}

}
