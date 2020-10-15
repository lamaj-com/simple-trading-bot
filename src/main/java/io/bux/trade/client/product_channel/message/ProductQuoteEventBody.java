package io.bux.trade.client.product_channel.message;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * Represents a body of a message which is of
 * {@link ProductChannelEventType#PRODUCT_QUOTE_MESSAGE} type.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductQuoteEventBody {

	private String securityId; // represents a productId
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal currentPrice;
	private Long timeStamp;

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

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timestamp) {
		this.timeStamp = timestamp;
	}
}
