package io.bux.trade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import io.bux.trade.client.product_channel.WebSocketHandler;

/**
 * 
 * Configures {@link WebSocketConnectionManager} for connecting to a WebSocket
 * server.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "spring.application.client.productchannel")
public class WebSocketConnectionManagerConfig {

	@Autowired
	private WebSocketHandler handler;

	private String baseUrl;

	private String authToken;
	
	

	@Bean
	public WebSocketConnectionManager wsConnectionManager() {
		WebSocketConnectionManager manager = new WebSocketConnectionManager(webSocketClient(), handler, this.baseUrl);
		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer" + " " + this.authToken);
		headers.set(HttpHeaders.ACCEPT_LANGUAGE, "nl-NL,en;q=0.8");
		manager.setHeaders(headers);
		manager.setAutoStartup(true); // so that the connection occurs when the Spring ApplicationContext is refreshed
		return manager;
	}


	@Bean
	public WebSocketClient webSocketClient() {
		WebSocketClient client = new StandardWebSocketClient();
		return client;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}