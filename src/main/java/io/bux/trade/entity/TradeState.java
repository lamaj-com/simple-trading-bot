package io.bux.trade.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.bux.trade.client.trade_api.model.TradeDirection;

/**
 * 
 * Represents a current trading state of the product that is monitored - whether
 * position is OPEN/CLOSE
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Entity
public class TradeState {

	@Id
	private String productId;
	private TradeDirection tradeDirection;
	private String positionId;

	public TradeState(String productId, String positionId, TradeDirection tradeDirection) {
		this.productId = productId;
		this.positionId = positionId;
		this.setTradeDirection(tradeDirection);
	}

	public TradeState() {
		super();
		this.setTradeDirection(TradeDirection.BUY);
	}

	public TradeState(String productId) {
		super();
		this.setProductId(productId);
		this.setTradeDirection(TradeDirection.BUY);
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public TradeDirection getTradeDirection() {
		return tradeDirection;
	}

	public void setTradeDirection(TradeDirection tradeDirection) {
		this.tradeDirection = tradeDirection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TradeState [productId=").append(productId).append(", tradeDirection=").append(tradeDirection)
				.append(", positionId=").append(positionId).append("]");
		return builder.toString();
	}

}
