package io.bux.trade.client.product_channel.message;

/**
 * 
 * Represents a type of an event coming from product channel
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public enum ProductChannelEventType {

	SUCCESSFUL_CONNECTION_MESSAGE("connect.connected"),

	UNSUCCESSFUL_CONNECTION_MESSAGE("connect.failed"),

	PRODUCT_QUOTE_MESSAGE("trading.quote");

	String typeValue;

	public String getTypeValue() {
		return typeValue;
	}

	ProductChannelEventType(String typeValue) {
		this.typeValue = typeValue;
	}

}
