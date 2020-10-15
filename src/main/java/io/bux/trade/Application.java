package io.bux.trade;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Starts the Trading Bot that tracks the price of a certain product (defined by
 * the application.productId arg) and will execute a pre-defined trade (BUY or
 * SELL) in the said product when it reaches a given price.
 * 
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@SpringBootApplication
public class Application implements ApplicationRunner {

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		LOGGER.info("Trading Bot started with the arguments: {}", Arrays.toString(args.getSourceArgs()));
	}

}
