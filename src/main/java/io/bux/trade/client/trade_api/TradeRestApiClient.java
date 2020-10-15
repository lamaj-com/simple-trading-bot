package io.bux.trade.client.trade_api;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.bux.trade.client.trade_api.model.TradeType;
import io.bux.trade.client.trade_api.request.BuyOrderRequest;
import io.bux.trade.client.trade_api.response.BuyOrderResponse;
import io.bux.trade.client.trade_api.response.SellOrderResponse;

/**
 * 
 * A rest client for communication with Trade REST API
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Component
public class TradeRestApiClient {

	private RestTemplate restTemplate;

	@Value("${spring.application.client.tradeRestApi.buyOrderUri}")
	private String buyOrderUri;

	@Value("${spring.application.client.tradeRestApi.sellOrderUri}")
	private String sellOrderUri;

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeRestApiClient.class);

	@Autowired
	public TradeRestApiClient(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	/**
	 * 
	 * Makes a trade and opens a position.
	 * 
	 * @return position id or null in the case if Trade REST API exception occurs
	 * 
	 */
	public String placeBuyOrder(BigDecimal currentPrice, String productId) {
		BuyOrderRequest buyRequest = new BuyOrderRequest(productId, currentPrice);
		ResponseEntity<BuyOrderResponse> buyOrderResponse = restTemplate.exchange(this.buyOrderUri, HttpMethod.POST,
				new HttpEntity<>(buyRequest), BuyOrderResponse.class);
		if (buyOrderResponse.getStatusCode().equals(HttpStatus.OK)
				&& (TradeType.OPEN.toString()).equals(buyOrderResponse.getBody().getType())) {
			LOGGER.info("Order for buying the product with id: '{}' placed successfully.", productId);
			return buyOrderResponse.getBody().getPositionId();
		} else {
			LOGGER.info("Buy order for product with id: '{}' not placed. See error log.", productId);
			return null;
		}
	}

	/**
	 * 
	 * Closes a position.
	 * 
	 * @return true in the case sell order was placed successfully, false otherwise
	 * 
	 */
	public boolean placeSellOrder(String positionId) {
		ResponseEntity<SellOrderResponse> sellOrderResponse = restTemplate.exchange(this.sellOrderUri + positionId,
				HttpMethod.DELETE, null, SellOrderResponse.class);
		if (sellOrderResponse.getStatusCode().equals(HttpStatus.OK) && sellOrderResponse.getBody() != null
				&& (TradeType.CLOSE.toString()).equals(sellOrderResponse.getBody().getType())) {
			LOGGER.info("Sell order with positionId: '{}' placed successfully.", positionId);
			return true;
		} else {
			LOGGER.info("Sell order with positionId: '{}' not placed. See error log.", positionId);
			return false;
		}
	}

}
