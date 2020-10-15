package io.bux.trade.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import io.bux.trade.client.product_channel.WebSocketHandler;
import io.bux.trade.client.trade_api.TradeRestApiClient;
import io.bux.trade.client.trade_api.model.ProductQuote;
import io.bux.trade.client.trade_api.model.TradeDirection;
import io.bux.trade.config.ApplicationSettings;
import io.bux.trade.entity.TradeState;
import io.bux.trade.repository.jpa.TradeStateRepository;

/**
 * A service for interaction with Trade REST API
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Service
public class TradeService {

	private TradeRestApiClient tradeRestApiClient;

	private ApplicationSettings applicationSettings;

	private TradeStateRepository tradeStateRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

	@Autowired
	public TradeService(TradeRestApiClient tradeRestApiClient, ApplicationSettings applicationSettings,
			TradeStateRepository tradeStateRepository) {
		super();
		this.tradeRestApiClient = tradeRestApiClient;
		this.applicationSettings = applicationSettings;
		this.tradeStateRepository = tradeStateRepository;
	}

	/**
	 * Performs a trading operation.
	 * 
	 * @param productQuote
	 *            a product quote
	 * @return an instance of TradeState project representing a current trade
	 *         position
	 *
	 */
	@Transactional
	public TradeState trade(ProductQuote productQuote) {
		if (!applicationSettings.getProductId().equals(productQuote.getSecurityId())) {
			LOGGER.debug("Product ids do not match.");
			throw new IllegalArgumentException("Product ids do not match.");
		}
		// Fetch the current trading state of the monitored product or create if it does
		// not exist
		TradeState tradeState = this.findProductById();
		if (tradeState == null) {
			tradeState = tradeStateRepository.save(new TradeState(applicationSettings.getProductId()));
		}

		if (TradeDirection.BUY.equals(tradeState.getTradeDirection())) {
			if (productQuote.getCurrentPrice().compareTo(applicationSettings.getBuyPrice()) <= 0) {
				String positionId = this.placeBuyOrder(productQuote);
				if (positionId != null) {
					tradeState.setTradeDirection(TradeDirection.SELL);
					tradeState.setPositionId(positionId);
				}
			}
		} else {
			if (productQuote.getCurrentPrice().compareTo(applicationSettings.getLowerSellingPrice()) >= 0
					&& productQuote.getCurrentPrice().compareTo(applicationSettings.getUpperSellingPrice()) <= 0) {
				boolean isSold = this.placeSellOrder(tradeState.getPositionId());
				if (isSold) {
					tradeState.setPositionId(null);
					tradeState.setTradeDirection(TradeDirection.BUY);
				}
			}

		}
		return tradeState;
	}

	/**
	 * Finds a trade state with the given product id or creates a new trade state if
	 * it does not exist
	 * 
	 * @return finds a {@link TradeState} object for the given product id
	 * 
	 */
	private TradeState findProductById() {
		Optional<TradeState> tradeState = tradeStateRepository.findById(applicationSettings.getProductId());
		if (tradeState.isPresent())
			return tradeState.get();
		else {
			LOGGER.debug("Trade state for the product with the id: {} not found.", applicationSettings.getProductId());
			return null;
		}
	}

	/**
	 * Performs a buy order.
	 * 
	 * @param productQuote
	 *            a product quote
	 * @return position id
	 *
	 */
	public String placeBuyOrder(ProductQuote productQuote) {
		String positionId = tradeRestApiClient.placeBuyOrder(productQuote.getCurrentPrice(),
				productQuote.getSecurityId());
		if (StringUtils.hasText(positionId)) {
			LOGGER.debug("Buy order placed.");
			return positionId;
		} else {
			LOGGER.debug("Buy order was not placed.");
			return null;
		}
	}

	/**
	 * Performs a sell order.
	 * 
	 * @param productQuote
	 *            a product quote
	 *
	 */
	public boolean placeSellOrder(String positionId) {
		LOGGER.debug("Placing sell order.");
		return tradeRestApiClient.placeSellOrder(positionId);
	}

}
