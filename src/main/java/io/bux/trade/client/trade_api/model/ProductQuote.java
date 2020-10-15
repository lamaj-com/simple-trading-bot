package io.bux.trade.client.trade_api.model;

import java.math.BigDecimal;

/**
 * 
 * Represents a product quote
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class ProductQuote {

	private String securityId;
	private BigDecimal currentPrice;
	private Long timestamp;

	public ProductQuote(String securityId, BigDecimal currentPrice, Long timestamp) {
		super();
		this.securityId = securityId;
		this.currentPrice = currentPrice;
		this.timestamp = timestamp;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
