package io.bux.trade.client.trade_api.exception;

/**
 * 
 * Maps to a request body received from the application that exposes Trade Rest
 * API when an error occurs on application level
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class RestError {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomError [message=").append(message).append(", developerMessage=").append(developerMessage)
				.append(", errorCode=").append(errorCode).append("]");
		return builder.toString();
	}

}
