package io.bux.trade.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketConnectionManager;

import io.bux.trade.client.product_channel.message.ManageSubscriptionEvent;
import io.bux.trade.config.ApplicationSettings;

/**
 * 
 * A service responsible for interacting with product channel
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Service
public class ProductChannelService {

	private ApplicationSettings applicationSettings;
	WebSocketConnectionManager wsConnectionManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductChannelService.class);

	@Autowired
	public ProductChannelService(ApplicationSettings applicationSettings, WebSocketConnectionManager wsConnectionManager) {
		super();
		this.applicationSettings = applicationSettings;
		this.wsConnectionManager = wsConnectionManager;
	}

	/**
	 * Reconnects to WebSocket server.
	 */
	public void reconnect() {
		LOGGER.debug("Reconnecting...");
		wsConnectionManager.stop();
		wsConnectionManager.start();
	}

	/**
	 * Creates a message to subscribe to the quote feed
	 * 
	 * @return a message
	 */
	public ManageSubscriptionEvent createSubscriptionMsg() {
		return new ManageSubscriptionEvent(Arrays.asList(applicationSettings.getProductId()));
	}

}
