package io.bux.trade.client.product_channel.exception;

/**
 * 
 * Exception to throw in case when a connection with product channel could not
 * be established due to the error on application level of product channel
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class ProductChannelConnectionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductChannelConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductChannelConnectionException(String message) {
		super(message);
	}

}
