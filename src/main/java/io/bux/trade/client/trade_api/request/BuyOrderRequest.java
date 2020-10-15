package io.bux.trade.client.trade_api.request;

import java.math.BigDecimal;

import io.bux.trade.client.trade_api.model.BigMoney;
import io.bux.trade.client.trade_api.model.TradeDirection;

/**
 * 
 * Represents a request body for placing a buy order
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class BuyOrderRequest {

	private String productId;
	private BigMoney investingAmount;
	private int leverage = 2;
	private String direction = TradeDirection.BUY.toString();
	private Source source;

	public BuyOrderRequest(String productId, BigDecimal amount) {
		super();
		this.productId = productId;
		this.investingAmount = new BigMoney(amount);
		this.source = new Source("OTHER");
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigMoney getInvestingAmount() {
		return investingAmount;
	}

	public void setAmount(BigDecimal amount) {
		investingAmount.setAmount(amount.toString());
	}

	public void setInvestingAmount(BigMoney investingAmount) {
		this.investingAmount = investingAmount;
	}

	public int getLeverage() {
		return leverage;
	}

	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	class Source {

		String sourceType;

		public String getSourceType() {
			return sourceType;
		}

		public void setSourceType(String sourceType) {
			this.sourceType = sourceType;
		}

		public Source(String sourceType) {
			this.sourceType = sourceType;
		}

	}

}
