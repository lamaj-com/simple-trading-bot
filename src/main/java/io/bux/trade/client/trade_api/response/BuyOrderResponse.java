package io.bux.trade.client.trade_api.response;

import io.bux.trade.client.trade_api.model.BigMoney;
import io.bux.trade.client.trade_api.model.Product;

/**
 * 
 * Represents a response body for placing a buy order
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
public class BuyOrderResponse {

	private String id;
	private String positionId;
	private Product product;
	private BigMoney investingAmount;
	private BigMoney price;
	private int leverage;
	private String direction;
	private String type;
	private long dateCreated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigMoney getInvestingAmount() {
		return investingAmount;
	}

	public void setInvestingAmount(BigMoney investingAmount) {
		this.investingAmount = investingAmount;
	}


	public int getLeverage() {
		return leverage;
	}

	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuyOrderResponse [id=").append(id).append(", positionId=").append(positionId)
				.append(", product=").append(product).append(", investingAmount=").append(investingAmount)
				.append(", price=").append(price).append(", leverage=").append(leverage).append(", direction=")
				.append(direction).append(", type=").append(type).append(", dateCreated=").append(dateCreated)
				.append("]");
		return builder.toString();
	}

}
