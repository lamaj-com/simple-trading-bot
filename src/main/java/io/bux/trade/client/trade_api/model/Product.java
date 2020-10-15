package io.bux.trade.client.trade_api.model;

/**
 * 
 * Represents a product to perform a trading operation on
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class Product {

	String securityId;
	String symbol;
	String displayName;

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [securityId=").append(securityId).append(", symbol=").append(symbol)
				.append(", displayName=").append(displayName).append("]");
		return builder.toString();
	}
	
	

}
