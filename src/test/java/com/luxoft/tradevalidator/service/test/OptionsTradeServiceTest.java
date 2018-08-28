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
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.service.impl.TradeServiceImpl;
import com.luxoft.tradevalidator.validator.OptionsTradeValidator;
import com.luxoft.tradevalidator.validator.TradeValidator;
import com.luxoft.tradevalidator.validator.impl.ExcerciseStartDateAfterExpiryDateValidatorImpl;
import com.luxoft.tradevalidator.validator.impl.ExcerciseStartDateBeforeTradeDateValidatorImpl;
import com.luxoft.tradevalidator.validator.impl.ExpiryDateAfterDeliveryDateValidatorImpl;
import com.luxoft.tradevalidator.validator.impl.PremiumDateAfterDeliveryDateValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class OptionsTradeServiceTest extends GeneralTradeServiceTest {
	
	@Spy
	private Map<TradeType, List<? extends TradeValidator>> specificValidators = new HashMap<>();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Mock
	private ParameterConfigRepository parameterRepository;
	
	@Mock
	private CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Spy
	@InjectMocks
	private TradeServiceImpl tradeService;
	
	@Before
	public void setup(){
		
		super.setup();
		List<OptionsTradeValidator> validators = new ArrayList<>();
		validators.add(new ExcerciseStartDateBeforeTradeDateValidatorImpl());
		validators.add(new ExcerciseStartDateAfterExpiryDateValidatorImpl());
		validators.add(new ExpiryDateAfterDeliveryDateValidatorImpl());
		validators.add(new PremiumDateAfterDeliveryDateValidatorImpl());
		
		specificValidators.put(TradeType.OPTIONS, validators);
	}

	@Test
	public void testValidateAmericanStyleWithExcerciseStartDateBeforeTradeDate() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 6));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 7));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 10));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Exercise Start Date' cannot be before 'Trade Date'"));
		
		tradeService.validate(tradeList);
		
	}
	
	@Test
	public void testValidateAmericanStyleWithExcerciseStartDateAfterExpiryDate() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 6));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 7));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Exercise Start Date' cannot be after 'Expiry Date'"));
		
		tradeService.validate(tradeList);
		
	}
	
	@Test
	public void testValidateExpiryDateAfterDeliveryDate() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 10));
		tradeData.setDeliveryDate(LocalDate.of(2018, 8, 9));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		expectedException.expect(TradeValidationException.class);
		expectedException.expectMessage(Matchers.containsString("'Expiry Date' cannot be after 'Delivery Date'"));
		
		tradeService.validate(tradeList);
	}
	
	@Test
	public void testValidateValidTrade() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		tradeService.validate(tradeList);
		
	}
	
	private TradeData createDefaultValidTradeData() {
		TradeData tradeData = TradeData.builder()
			.customer(CustomerType.PLUTO1)
			.ccyPair("EURUSD")
			.type(TradeType.OPTIONS)
			.style(TradeStyle.EUROPEAN)
			.direction(DirectionType.BUY)
			.valueDate(LocalDate.of(2018, 8, 8))
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
