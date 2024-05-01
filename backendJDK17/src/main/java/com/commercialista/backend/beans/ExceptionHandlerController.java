package com.commercialista.backend.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.commercialista.backend.dto.exchange.WsProblema;
import com.commercialista.backend.exception.BeException;
import com.commercialista.backend.exception.ProblemaException;
import com.commercialista.backend.exception.ProblemaGenericoException;
import com.commercialista.backend.exception.ProblemaValidazioneException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);
	@Value("${http.error.trace:false}")
	private boolean errorTrace;
	@Autowired
	private ObjectMapper objectMapper;


	@ExceptionHandler({ ProblemaException.class, BeException.class, HttpClientErrorException.class })
	public ResponseEntity<Object> eccezioneGestita(RuntimeException e) {
		log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage());
		log.error("Eccezione gestita", e);
		return eccezione(e);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> eccezioneNonGestita(Exception e) {
		log.error("Eccezione non gestita durante l'esecuzione", e);
		return eccezione(new ProblemaGenericoException(e));
	}

	// @NonNull
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			@NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
		return eccezioneGestita(new ProblemaValidazioneException(ex.getFieldErrors()));
	}

	public ResponseEntity<Object> eccezione(RuntimeException e) {
		if (e instanceof BeException) {
			BeException ex = (BeException) e;
			WsProblema wsProblema = new WsProblema(ex.getCodiceErrore(), String.valueOf(ex.getHttpStatus().value()), "",
					ex.getMessaggioErrore());
			return ResponseEntity.status(ex.getHttpStatus().value()).body(wsProblema);
		} else {
			ProblemaException ex = null;
			if (e instanceof ProblemaException) {
				ex = (ProblemaException) e;
			} else if (e instanceof HttpClientErrorException) {
				ex = castB2BExceptionToProblemaException((HttpClientErrorException) e, objectMapper);
			} else {
				ex = new ProblemaGenericoException(e);
			}
			WsProblema wsProblema = new WsProblema(ex.getCodice(), String.valueOf(ex.getCodiceHttp()), ex.getTitolo(),
					ex.getDescrizione());
			return ResponseEntity.status(ex.getCodiceHttp()).body(wsProblema);
		}
	}

	@NonNull
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, @Nullable Object body,
			@Nullable HttpHeaders headers, @NonNull HttpStatusCode status, @Nullable WebRequest request) {
		if (errorTrace) {
			log.error("Spring MVC exception", ex);
			return eccezione(new ProblemaGenericoException(ex));
		} else {
			return eccezioneNonGestita(ex);
		}
	}

	private ProblemaException castB2BExceptionToProblemaException(HttpClientErrorException e,
			ObjectMapper objectMapper) {
		try {
			String jsonPart = e.getMessage().substring(e.getMessage().indexOf(':') + 1).trim();
			String withoutQuotes = jsonPart.substring(1, jsonPart.length() - 1);
			JsonNode jsonNode = objectMapper.readValue(withoutQuotes, JsonNode.class);
			if (jsonNode.get("codice") != null && jsonNode.get("stato") != null && jsonNode.get("titolo") != null
					&& jsonNode.get("descrizione") != null) {
				return new ProblemaException(jsonNode.get("codice").asText(), jsonNode.get("stato").asInt(),
						jsonNode.get("titolo").asText(), jsonNode.get("descrizione").asText());
			} else {
				throw e;
			}
		} catch (JsonProcessingException ex) {
			throw new ProblemaGenericoException(ex);
		}
	}
}