package com.commercialista.backend.client;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.commercialista.backend.log.LogUtil;


public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		String idTransazione = MDC.get(LogUtil.id_transazione);

		if (idTransazione == null) {
			request.getHeaders().add(LogUtil.id_transazione, LogUtil.getIdTransazione());
		} else {
			request.getHeaders().add(LogUtil.id_transazione, idTransazione);
		}

		String idSessione = MDC.get(LogUtil.id_sessione);

		if (idSessione == null) {
			request.getHeaders().add(LogUtil.id_sessione, LogUtil.getIdSessione());
		} else {
			request.getHeaders().add(LogUtil.id_sessione, idSessione);
		}

		String cfUtente = MDC.get(LogUtil.cf_utente);

		if (cfUtente == null) {
			request.getHeaders().add(LogUtil.cf_utente, LogUtil.getCfUtente());
		} else {
			request.getHeaders().add(LogUtil.cf_utente, cfUtente);
		}

		String requestUri = MDC.get(LogUtil.request_uri);

		if (requestUri == null) {
			request.getHeaders().add(LogUtil.request_uri, LogUtil.getRequestUri());
		} else {
			request.getHeaders().add(LogUtil.request_uri, requestUri);
		}

		String queryString = MDC.get(LogUtil.query_string);

		if (queryString == null) {
			request.getHeaders().add(LogUtil.query_string, LogUtil.getQueryString());
		} else {
			request.getHeaders().add(LogUtil.query_string, queryString);
		}

		String clientIp = MDC.get(LogUtil.client_ip);

		if (clientIp == null) {
			request.getHeaders().add(LogUtil.client_ip, LogUtil.getClientIp());
		} else {
			request.getHeaders().add(LogUtil.client_ip, clientIp);
		}

		String webMethod = MDC.get(LogUtil.web_method);

		if (webMethod == null) {
			request.getHeaders().add(LogUtil.web_method, LogUtil.getWebMethod());
		} else {
			request.getHeaders().add(LogUtil.web_method, webMethod);
		}
		
		String doNotLog = MDC.get(LogUtil.do_not_log);
		if (doNotLog == null) {
			request.getHeaders().add(LogUtil.do_not_log, LogUtil.getDoNotLog());
		} else {
			request.getHeaders().add(LogUtil.do_not_log, doNotLog);
		}

		return execution.execute(request, body);
	}

}
