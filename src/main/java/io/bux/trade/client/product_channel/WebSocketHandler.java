package io.bux.trade.client.product_channel;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.bux.trade.client.product_channel.exception.ProductChannelConnectionException;
import io.bux.trade.client.product_channel.message.Event;
import io.bux.trade.client.product_channel.message.ProductChannelEventType;
import io.bux.trade.client.product_channel.message.ProductQuoteEventBody;
import io.bux.trade.client.trade_api.model.ProductQuote;
import io.bux.trade.service.ProductChannelService;
import io.bux.trade.service.TradeService;

/**
 * Processes messages coming from Product Channel. Whether exceptions occurs it
 * will be logged and the session will be closed with
 * {@link CloseStatus#SERVER_ERROR SERVER_ERROR(1011)}, unless handled by
 * {@link org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator
 * ExceptionWebSocketHandlerDecorator}.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private TradeService tradeService;

	@Autowired
	private ProductChannelService productChannelService;
	

	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.debug("Session {} is open.", session.getId());
		super.afterConnectionEstablished(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.debug("Session {} is closed.", session.getId());
		super.afterConnectionClosed(session, status);
		// Session will be closed if any runtime exception occurs while handling the
		// message. To retry add status.getCode()==1011
		if (status.getCode() == 1006 || status.getCode() == 1012) {
			productChannelService.reconnect();
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		LOGGER.info("Message received: {} ...", message.getPayload());
		JsonNode payload = objectMapper.readValue(message.getPayload(), JsonNode.class);
		if (payload.get("t") == null) {
			LOGGER.debug("Message not valid. Skipping...", message.getPayload());
		} else {
			Event productChannelMsg = objectMapper.readValue(message.getPayload(), Event.class);
			if (productChannelMsg.getType()
					.equals(ProductChannelEventType.SUCCESSFUL_CONNECTION_MESSAGE.getTypeValue())) {
				// Subscribe to product quote channel
				session.sendMessage(new TextMessage(
						objectMapper.writeValueAsString(productChannelService.createSubscriptionMsg())));
			} else if (productChannelMsg.getType()
					.equals(ProductChannelEventType.UNSUCCESSFUL_CONNECTION_MESSAGE.getTypeValue())) {
				throw new ProductChannelConnectionException("Unsuccessful connection to product quote channel due to: "
						+ productChannelMsg.getBody().get("errorCode").textValue());
			} else if (productChannelMsg.getType()
					.equals(ProductChannelEventType.PRODUCT_QUOTE_MESSAGE.getTypeValue())) {
				ProductQuoteEventBody productQuoteMsgBody = objectMapper.treeToValue(productChannelMsg.getBody(),
						ProductQuoteEventBody.class);
				// Convert product quote msg received from product channel to domain product
				// quote domain
				ProductQuote productQuote = new ProductQuote(productQuoteMsgBody.getSecurityId(),
						productQuoteMsgBody.getCurrentPrice(), productQuoteMsgBody.getTimeStamp());
				tradeService.trade(productQuote);
			} else {
				LOGGER.debug("Unexpected message type: {}", message);
			}
		}

	}
//
//	@Autowired
//	public WebSocketHandler(TradeService tradeService, ProductChannelService productChannelService) {
//		super();
//		this.tradeService = tradeService;
//		this.productChannelService = productChannelService;
//	}

}