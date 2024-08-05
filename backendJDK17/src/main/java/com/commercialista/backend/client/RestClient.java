package com.commercialista.backend.client;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class RestClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

	private RestClient() {
	}

	private static void logRequest(String url, String method, Object body) {
		try {
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			 om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			// om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
			// om.findAndRegisterModules();
			String bodyString = om.writeValueAsString(body);

			LOGGER.info("url: {} method: {} requestBody: {}", url, method, bodyString);
		} catch (JsonProcessingException e) {
			LOGGER.error("logRequest: " + e.getMessage(), e);
		}
	}

	private static void logMultipartRequest(String url, String method, Object body) {
		try {
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			// om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
			// om.findAndRegisterModules();

			MultiValueMap<String, Object> bodyMap = (MultiValueMap<String, Object>) body;
			StringBuilder bodyString = new StringBuilder();
			for (String key : bodyMap.keySet()) {
				for (Object value : bodyMap.get(key)) {
					bodyString.append(System.lineSeparator());
					bodyString.append("Part " + key + System.lineSeparator());
					if (value instanceof ByteArrayResource) {
						ByteArrayResource byteArrayResource = (ByteArrayResource) value;
						if (byteArrayResource.getByteArray() != null) {
							bodyString.append("Byte array " + byteArrayResource.getByteArray().length);
						} else {
							bodyString.append("Byte array");
						}
					} else {
						bodyString.append(om.writeValueAsString(value));
					}
					bodyString.append(System.lineSeparator());
					bodyString.append("---------------------------");
				}
			}
			LOGGER.info("url: {} method: {} requestBody: {}", url, method, bodyString);
		} catch (Exception e) {
			LOGGER.error("logRequest: " + e.getMessage(), e);
        }
    }

	private static void logResponse(Object body) {
		try {
			ObjectMapper om = new ObjectMapper();
			om.registerModule(new JavaTimeModule());
			// om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
			// om.findAndRegisterModules();
			String bodyString = om.writeValueAsString(body);

			LOGGER.info("responseBody: {}", bodyString);
		} catch (JsonProcessingException e) {
			LOGGER.error("logResponse: " + e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private static RestTemplate getRestTemplate(String url) {
		long start = new Date().getTime();
		
		RestTemplate restTemplate;
		if (url.startsWith("https:")) {
			restTemplate = getRestTemplate(true, false);
		} else {
			restTemplate = getRestTemplate(false, false);
		}
		
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return restTemplate;
	}
	
	/**
	 * 
	 * @param disableCertificateValidation
	 * @param enablePoolConnection
	 * @return
	 */
	private static RestTemplate getRestTemplate(boolean disableCertificateValidation, boolean enablePoolConnection) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  //HttpClients.custom();
		PoolingHttpClientConnectionManager cm; //new PoolingHttpClientConnectionManager();	
		
//		if (enablePoolConnection) {
//			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//			connectionManager.setMaxTotal(500);
//			connectionManager.setDefaultMaxPerRoute(500);
//			httpClientBuilder.setConnectionManager(connectionManager);
//		}

		if (disableCertificateValidation) {
			try {
				TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
				SSLContext sslContext = SSLContexts.custom()
						.loadTrustMaterial(null, acceptingTrustStrategy).build();
				
				SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
		                .setSslContext(sslContext)
		                .setHostnameVerifier(new NoopHostnameVerifier())
		                .build();
				
				 cm = PoolingHttpClientConnectionManagerBuilder.create()
		                .setSSLSocketFactory(sslSocketFactory)
		                .build();
				
				//SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
				//httpClientBuilder.setSSLSocketFactory(csf);
				httpClientBuilder.setConnectionManager(cm);

			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
				cm = PoolingHttpClientConnectionManagerBuilder.create().build();
				LOGGER.error(e.getMessage(), e);
			}
		}
		else
		{
			cm =  PoolingHttpClientConnectionManagerBuilder.create().build();
			
		}
		
		

		
//		clientHttpRequestFactory.setHttpClient(httpClientBuilder.build());
//		clientHttpRequestFactory.setConnectTimeout(0);
//		
//		//clientHttpRequestFactory.setReadTimeout(0);
//		clientHttpRequestFactory.setConnectionRequestTimeout(0);
		
		
		////
		// Connect timeout
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(0))
                .build();

        // Socket timeout
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofMilliseconds(0))
                .build();

        // Connection request timeout
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(0))
                .build();
        
        
        cm.setDefaultSocketConfig(socketConfig);
        cm.setDefaultConnectionConfig(connectionConfig);
        
        
     

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .build();
        
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
		////

		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RestTemplateHeaderModifierInterceptor());
		restTemplate.setInterceptors(interceptors);
		
		return restTemplate;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @return
	 */
	public static <T> ResponseEntity<T> send(String url,
											 String method,
											 HttpHeaders httpHeaders,
											 Object body,
											 Class<T> responseType) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);
		
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		
		logRequest(url, method, body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(method), requestEntity, responseType);
		logResponse(responseEntity.getBody());
		
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return responseEntity;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param body
	 * @param responseType
	 * @return
	 */
	public static <T> List<T> send(String url,
								   String method,
								   Object body,
								   ParameterizedTypeReference<List<T>> responseType) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		logRequest(url, method, body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<List<T>> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(method), requestEntity, responseType);
		List<T> out = responseEntity.getBody();
		
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return out;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param body
	 * @param responseType
	 * @return
	 */
	public static <T> T
	send(String url, String method, Object body, Class<T> responseType) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		HttpHeaders httpHeaders = new HttpHeaders();
		if (body != null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}
		logRequest(url, method, body);
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.valueOf(method), requestEntity, responseType);
		
		T out = responseEntity.getBody();
		logResponse(out);
		
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return out;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @return
	 */
	public static <T> T send(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, Class<T> responseType) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		logRequest(url, method.toString(), body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
		T out = responseEntity.getBody();
		
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return out;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> T sendWithUriVariables(String url,
											 HttpMethod method,
											 HttpHeaders httpHeaders,
											 Object body,
											 Class<T> responseType,
											 Object... uriVariables) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		if (httpHeaders.getContentType() == null && body != null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}

		if (httpHeaders.getContentType() != null && httpHeaders.getContentType().equals(MediaType.MULTIPART_FORM_DATA)) {
			logMultipartRequest(url, method.toString(), body);
		} else {
			logRequest(url, method.toString(), body);
		}
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
		T out = responseEntity.getBody();
		
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return out;
	}

	/**
	 *
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> ResponseEntity<T> sendWithUriVariablesCompleteResponse(String url,
															 HttpMethod method,
															 HttpHeaders httpHeaders,
															 Object body,
															 Class<T> responseType,
															 Object... uriVariables) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		if (httpHeaders.getContentType() == null && body != null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}

		if (httpHeaders.getContentType() != null && httpHeaders.getContentType().equals(MediaType.MULTIPART_FORM_DATA)) {
			logMultipartRequest(url, method.toString(), body);
		} else {
			logRequest(url, method.toString(), body);
		}

		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);

		logResponse(responseEntity);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);

		return responseEntity;
	}

	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> T sendWithUriVariables(String url,
											 HttpMethod method,
											 HttpHeaders httpHeaders,
											 Object body,
											 ParameterizedTypeReference<T> responseType,
											 Object... uriVariables) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		if (httpHeaders.getContentType() == null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}

		logRequest(url, method.toString(), body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
		T out = responseEntity.getBody();
		
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return out;
	}

	/**
	 * 
	 * @param <T>
	 * @param httpUrl
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @param parameters
	 * @param uriVariables
	 * @return
	 */
	public static <T> T sendWithUriVariables(String httpUrl,
											 HttpMethod method,
											 HttpHeaders httpHeaders,
											 Object body,
											 ParameterizedTypeReference<T> responseType,
											 Map<String, Object> parameters,
											 Object... uriVariables) {
		long start = new Date().getTime();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			if (entry.getValue() instanceof Collection) {
				uriComponentsBuilder.queryParam(entry.getKey(), ((Collection) entry.getValue()).stream().collect(Collectors.joining(",")));
			} else {
				uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
			}
		}
		String url = uriComponentsBuilder.toUriString();

		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		logRequest(url, method.toString(), body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);


		ResponseEntity<T> responseEntity = restTemplate.exchange(uriComponentsBuilder.build(uriVariables), method, requestEntity, responseType);
		T out = responseEntity.getBody();
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		return out;
	}

	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param method
	 * @param httpHeaders
	 * @param body
	 * @param responseType
	 * @param uriVariables
	 * @return
	 */
	public static <T> ResponseEntity<T> sendRequest(String url,
													HttpMethod method,
													HttpHeaders httpHeaders,
													Object body,
													ParameterizedTypeReference<T> responseType,
													Object... uriVariables) {
		long start = new Date().getTime();
		RestTemplate restTemplate = getRestTemplate(url);

		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		logRequest(url, method.toString(), body);
		HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
		T out = responseEntity.getBody();
		
		logResponse(out);
		long end = new Date().getTime();
		long app = end - start;
		LOGGER.debug("tempo esecuzione: {}", app);
		
		return responseEntity;
	}
	
}
