package io.bux.trade.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import io.bux.trade.Application;
import io.bux.trade.client.trade_api.TradeRestApiClient;
import io.bux.trade.client.trade_api.model.ProductQuote;
import io.bux.trade.client.trade_api.model.TradeDirection;
import io.bux.trade.config.ApplicationSettings;
import io.bux.trade.entity.TradeState;
import io.bux.trade.repository.jpa.TradeStateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { PersistenceContext.class, Application.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TradeServiceTest {

	@MockBean
	TradeRestApiClient tradeRestApiClient = Mockito.mock(TradeRestApiClient.class);
	@MockBean
	ApplicationSettings applicationSettings = Mockito.mock(ApplicationSettings.class);

	@Autowired
	TradeStateRepository tradeStateRepository;

	TradeService tradeService;

	@Before
	public void init() {
		applicationSettings = new ApplicationSettings("sb26496", new BigDecimal(11.0), new BigDecimal(15),
				new BigDecimal(9));
		tradeService = new TradeService(tradeRestApiClient, applicationSettings, tradeStateRepository);
	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceLessThanBuyPriceTest() {
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(10.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(10.0), 1602694113L));
		assertEquals(tradeState.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");

	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceEqualsBuyPriceTest() {
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(11.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(11.0), 1602694113L));
		assertEquals(tradeState.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");

	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceGreaterThanBuyPriceAndBuyDirectionTest() {
		TradeState tradeState = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(12.0), 1602694113L));
		assertNull(tradeState.getPositionId());
		assertEquals(tradeState.getTradeDirection(), TradeDirection.BUY);

	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceInBetweenLimitsBuyAndSellTest() {
		// First buy
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(10.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState1 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(10.0), 1602694113L));
		assertEquals(tradeState1.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		assertEquals(tradeState1.getTradeDirection(), TradeDirection.SELL);
		// Then sell
		Mockito.when(tradeRestApiClient.placeSellOrder("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc")).thenReturn(true);
		TradeState tradeState2 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(14.0), 1602694114L));
		assertEquals(tradeState2.getTradeDirection(), TradeDirection.BUY);
		assertNull(tradeState2.getPositionId());
	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceEqualsLowerSellLimitAndSellDirectionTest() {
		// First buy
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(10.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState1 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(10.0), 1602694113L));
		assertEquals(tradeState1.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		assertEquals(tradeState1.getTradeDirection(), TradeDirection.SELL);
		// Then sell
		Mockito.when(tradeRestApiClient.placeSellOrder("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc")).thenReturn(true);
		TradeState tradeState2 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(9.0), 1602694114L));
		assertEquals(tradeState2.getTradeDirection(), TradeDirection.BUY);
		assertNull(tradeState2.getPositionId());
	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceLessThanLowerSellLimitAndSellDirectionTest() {
		// First buy
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(10.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState1 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(10.0), 1602694113L));
		assertEquals(tradeState1.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		assertEquals(tradeState1.getTradeDirection(), TradeDirection.SELL);
		// Then sell
		Mockito.when(tradeRestApiClient.placeSellOrder("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc")).thenReturn(true);
		TradeState tradeState2 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(8.0), 1602694114L));
		assertEquals(tradeState2.getTradeDirection(), TradeDirection.SELL);
	}

	@Test
	@Transactional
	public void tradeWhenCurrentPriceEqualsUpperSellLimitAndSellDirectionTest() {
		// First buy
		Mockito.when(tradeRestApiClient.placeBuyOrder(new BigDecimal(10.0), "sb26496"))
				.thenReturn("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		TradeState tradeState1 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(10.0), 1602694113L));
		assertEquals(tradeState1.getPositionId(), "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc");
		assertEquals(tradeState1.getTradeDirection(), TradeDirection.SELL);
		// Then sell
		Mockito.when(tradeRestApiClient.placeSellOrder("4c58a0b2-ea78-46a0-ac21-5a8c22d527dc")).thenReturn(true);
		TradeState tradeState2 = tradeService.trade(new ProductQuote("sb26496", new BigDecimal(15.0), 1602694114L));
		assertEquals(tradeState2.getTradeDirection(), TradeDirection.BUY);
		assertNull(tradeState2.getPositionId());
	}

	@Test
	public void tradeInvalidProductId() {
		assertThrows(IllegalArgumentException.class,
				() -> tradeService.trade(new ProductQuote("sb26498", new BigDecimal(10.0), 1602694113L)));
	}

}
