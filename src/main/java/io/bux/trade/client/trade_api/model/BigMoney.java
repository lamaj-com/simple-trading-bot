package io.bux.trade.client.trade_api.model;

import java.math.BigDecimal;


/**
 * 
 * Represents an amount
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class BigMoney {

	private String currency = "BUX";
	private int decimals;
	private String amount;

	public BigMoney() {
		super();
		this.decimals = 2;
	}

	public BigMoney(BigDecimal amount) {
		super();
		this.amount = amount.toString(); // set to 10.0 for testing in Beta env
		this.decimals = Math.max(0, amount.stripTrailingZeros().scale());
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvestingAmount [currency=").append(currency).append(", decimals=").append(decimals)
				.append(", amount=").append(amount).append("]");
		return builder.toString();
	}

}
