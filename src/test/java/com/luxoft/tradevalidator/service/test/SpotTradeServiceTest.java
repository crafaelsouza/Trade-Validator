package com.luxoft.tradevalidator.service.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.CCPairExceptionSpotTrade;
import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.service.impl.TradeServiceImpl;
import com.luxoft.tradevalidator.validator.SpotTradeValidator;
import com.luxoft.tradevalidator.validator.TradeValidator;
import com.luxoft.tradevalidator.validator.impl.ValueDateForSpotTransactionValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class SpotTradeServiceTest extends GeneralTradeServiceTest {
	

	@Spy
	private Map<TradeType, List<? extends TradeValidator>> specificValidators = new HashMap<>();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Spy
	@InjectMocks
	private TradeServiceImpl tradeService;
	
	@Mock
	private ParameterConfigRepository parameterRepository;
	
	@Mock
	private CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Before
	public void setup(){
		super.setup();
		
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair("EURUSD")).willReturn(Collections.emptyList());
		BDDMockito.given(ccPairExceptionRepository.findAll()).willReturn(Collections.emptyList());
		ParameterConfig param1 = new ParameterConfig(1, ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE, "2");
		ParameterConfig param2 = new ParameterConfig(2, ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE, "1");
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE)).willReturn(param1);
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE)).willReturn(param2);
		
		List<SpotTradeValidator> validators = new ArrayList<>();
		validators.add(new ValueDateForSpotTransactionValidatorImpl(parameterRepository, ccPairExceptionRepository, bankHolidayRepository));
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(Collections.emptyList());
		specificValidators.put(TradeType.SPOT, validators);
	}
	
	@Test
	public void testValidateValueDateOneDayBeforeTradeDate() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Value Date' must be 4 days after 'Trade Date'"));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	@Test
	public void testValidateValueDateTwoDaysBeforeTradeDateCurrencyDefault() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	@Test
	public void testValidateValueDateOneMonthAfterTradeDate() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 9, 12));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 10));
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Value Date' must be 4 days after 'Trade Date'"));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	@Test
	public void testValidateValueDateOnHoliday() throws TradeValidationException {
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		tradeData.setCcyPair("EURUSD");
		
		BankHoliday b1 = new BankHoliday(1, "EURUSD", 13, 8, "Day Off");
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair("EURUSD")).willReturn(Arrays.asList(b1));
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Value Date' must be 5 days after 'Trade Date'"));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	
	@Test
	public void testValidateFallingAfterHolidays() throws TradeValidationException{
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 14));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		List<BankHoliday> holidays = Arrays.asList(new BankHoliday(1, "USDEUR", 13, 8, "Day OFF"));
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(holidays );
		
		tradeService.validate(Arrays.asList(tradeData));
		
	}
	
	@Test
	public void testValidateCurrencyPairInExceptionList() throws TradeValidationException{
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		BDDMockito.given(ccPairExceptionRepository.findAll()).willReturn(Arrays.asList(new CCPairExceptionSpotTrade(1, "USDEUR")));
		
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Value Date' must be 1 days after 'Trade Date'"));
		
		tradeService.validate(Arrays.asList(tradeData));
	}
	
	@Test
	public void testValidateFallingOnHolidays() throws TradeValidationException{
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		List<BankHoliday> holidays = Arrays.asList(new BankHoliday(1, "USDEUR", 13, 8, "Day OFF"));
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(holidays );
		
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Value Date' must be 5 days after 'Trade Date'"));
		
		tradeService.validate(Arrays.asList(tradeData));
		
	}
	
	private TradeData createDefaultValidTradeData() {
		TradeData tradeData = TradeData.builder()
			.customer(CustomerType.PLUTO1)
			.ccyPair("EURUSD")
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
