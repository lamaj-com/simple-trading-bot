package io.bux.trade.config;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 
 * Trading Bot Settings set on the application startup. These settings could be
 * configured via CLI or application.properties file.
 * 
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Configuration
public class ApplicationSettings {

	@Value("${spring.application.productId: #{null}}")
	private String productId; // Product id
	@Value("#{${spring.application.buyPrice: null} == null ? null : new java.math.BigDecimal(${spring.application.buyPrice: null})}")
	private BigDecimal buyPrice; // If the stock price doesn't reach that price the position shouldn't be opened.
	@Value("#{${spring.application.upperSellingPrice: null} == null ? null : new java.math.BigDecimal(${spring.application.upperSellingPrice: null})}")
	private BigDecimal upperSellingPrice; // This is the price you are willing to close a position and make a profit.
	@Value("#{${spring.application.lowerSellingPrice: null} == null ? null : new java.math.BigDecimal(${spring.application.lowerSellingPrice: null})}")
	private BigDecimal lowerSellingPrice; // This the price you want are willing to close a position at and make a loss.

	@PostConstruct
	public void init() {
		if (!StringUtils.hasText(productId))
			throw new IllegalArgumentException("[FATAL] Invalid command line arguments: Invalid product id!");
		if (buyPrice == null)
			throw new IllegalArgumentException("[FATAL] Invalid command line arguments: Invalid buying price!");
		if (upperSellingPrice == null)
			throw new IllegalArgumentException("[FATAL] Invalid command line arguments: Invalid upper selling price!");
		if (lowerSellingPrice == null)
			throw new IllegalArgumentException("[FATAL] Invalid command line arguments: Invalid lower selling price!");
		if (lowerSellingPrice.compareTo(buyPrice) >= 0)
			throw new IllegalArgumentException(
					"[FATAL] Invalid command line arguments: Lower selling price should be less than buying price!");
		if (buyPrice.compareTo(upperSellingPrice) >= 0) {
			throw new IllegalArgumentException(
					"[FATAL] Invalid command line arguments: Upper selling price should be greater than buying price!");
		}

	}

	public ApplicationSettings(String productId, BigDecimal buyPrice, BigDecimal upperSellingPrice,
			BigDecimal lowerSellingPrice) {
		super();
		this.productId = productId;
		this.buyPrice = buyPrice;
		this.upperSellingPrice = upperSellingPrice;
		this.lowerSellingPrice = lowerSellingPrice;
	}

	public ApplicationSettings() {
		super();
	}

	public String getProductId() {
		return productId;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public BigDecimal getUpperSellingPrice() {
		return upperSellingPrice;
	}

	public BigDecimal getLowerSellingPrice() {
		return lowerSellingPrice;
	}

}
