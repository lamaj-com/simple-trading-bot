package io.bux.trade.client.product_channel.message;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Represents outgoing message, sent to product channel to manage product quote
 * feed subscription
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class ManageSubscriptionEvent {

	private List<String> subscribeTo;
	// private List<String> unsubscribeFrom;

	public static final String SUBSCRIBE_TO_MESAGE_PREFIX = "trading.product.";

	public ManageSubscriptionEvent(List<String> subscribeTo) {
		super();
		this.subscribeTo = new LinkedList<>();
		for (String productId : subscribeTo) {
			this.addProduct(productId);
		}
	}

	public ManageSubscriptionEvent() {
		super();
	}

	private void addProduct(String productId) {
		subscribeTo.add(SUBSCRIBE_TO_MESAGE_PREFIX + productId);
	}

	public List<String> getSubscribeTo() {
		return subscribeTo;
	}

	public void setSubscribeTo(List<String> subscribeTo) {
		this.subscribeTo.clear();
		for (String productId : subscribeTo) {
			this.addProduct(productId);
		}
	}
	
	

}
