package io.bux.trade.client.product_channel.message;

/**
 * 
 * Represents a body of a message which is of
 * {@link ProductChannelEventType#UNSUCCESSFUL_CONNECTION_MESSAGE} type.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class FailureEventBody {

	private String message;
	private String developerMessage;
	private String errorCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
