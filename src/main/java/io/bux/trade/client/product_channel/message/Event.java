package io.bux.trade.client.product_channel.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * Represents all incoming messages coming from product channel.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	private JsonNode body; // the content of the event, which varies depending on the type of the event
							// being received
	@JsonProperty("t")
	private String type; // type of the event

	public JsonNode getBody() {
		return body;
	}

	public void setBody(JsonNode body) {
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public void setType(String t) {
		this.type = t;
	}

}
