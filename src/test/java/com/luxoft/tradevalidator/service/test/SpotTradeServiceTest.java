package com.luxoft.tradevalidator.service.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CurrencyPairType;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.service.impl.TradeServiceImpl;
import com.luxoft.tradevalidator.validator.SpotTradeValidator;
import com.luxoft.tradevalidator.validator.TradeValidator;
import com.luxoft.tradevalidator.validator.impl.ValueDateForSpotTransactionsValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class SpotTradeServiceTest extends GeneralTradeServiceTest {
	

	@Spy
	private Map<TradeType, List<? extends TradeValidator>> specificValidators = new HashMap<>();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Spy
	@InjectMocks
	private TradeServiceImpl tradeService;
	
	@Before
	public void setup(){
		super.setup();
		
		List<SpotTradeValidator> validators = new ArrayList<>();
		validators.add(new ValueDateForSpotTransactionsValidatorImpl());
		
		specificValidators.put(TradeType.SPOT, validators);
	}
	
	@Test
	public void testValidateValueDateOneDayBeforeTradeDate() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		tradeData.setPayCC(CurrencyType.USD);
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString(String.format(
				"'Value Date' must be %s business days after 'Trade Date' for %s transaction", 2, tradeData.getType())));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	@Test
	public void testValidateValueDateTwoDaysBeforeTradeDateCurrencyDefault() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		tradeData.setPayCC(CurrencyType.USD);
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	private TradeData createDefaultValidTradeData() {
		TradeData tradeData = TradeData.builder()
			.customer(CustomerType.PLUTO1)
			.ccyPair(CurrencyPairType.EURUSD)
			.payCC(CurrencyType.USD)
			.type(TradeType.SPOT)
			.style(TradeStyle.EUROPEAN)
			.direction(DirectionType.BUY)
			.valueDate(LocalDate.of(2018, 8, 10))
			.expiryDate(LocalDate.of(2018, 8, 10))
			.premiumDate(LocalDate.of(2018, 8, 10))
			.deliveryDate(LocalDate.of(2018, 12, 8))
			.tradeDate(LocalDate.of(2018, 8, 7))
			.amount1(BigDecimal.TEN)
			.amount2(BigDecimal.TEN)
			.rate(2.01D)
			.legalEntity(LegalEntityType.CS_ZURICH)
			.trader("Johan Baumfiddler")
			.build();
		return tradeData;
	}


	@Override
	protected TradeService getTradeService() {
		return this.tradeService;
	}


	@Override
	protected ExpectedException getExpectedException() {
		return this.expectedException;
	}
	
}
