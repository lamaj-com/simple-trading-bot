package io.bux.trade.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import io.bux.trade.client.trade_api.exception.RestTemplateErrorHandler;

/**
 * 
 * Synchronous client for HTTP communication with Trade API.
 * 
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "spring.application.client.traderestapi")
public class RestTemplateConfig {

	private String baseUrl;

	private String authToken;
	
	private int connectionTimeout;
	
	private int readTimeout;

	@Bean
	public RestTemplate getRestTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler())
				.uriTemplateHandler(new DefaultUriBuilderFactory(this.baseUrl))
				.interceptors(Collections.singletonList(new ClientHttpRequestInterceptor() {
					@Override
					public ClientHttpResponse intercept(HttpRequest request, byte[] body,
							ClientHttpRequestExecution execution) throws IOException {
						HttpHeaders headers = request.getHeaders();
						headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
						headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
						headers.add(HttpHeaders.AUTHORIZATION, getAuthToken());
						headers.add(HttpHeaders.ACCEPT_LANGUAGE, "nl-NL,en;q=0.8");
						return execution.execute(request, body);
					}
				})).setConnectTimeout(Duration.ofSeconds(connectionTimeout))
				.setReadTimeout(Duration.ofSeconds(readTimeout)).build();
		return restTemplate;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}



}
