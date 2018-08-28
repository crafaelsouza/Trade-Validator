package com.luxoft.tradevalidator.service.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.validator.GeneralTradeValidator;
import com.luxoft.tradevalidator.validator.impl.ValueDateBeforeTradeDateValidatorImpl;
import com.luxoft.tradevalidator.validator.impl.ValueDateFallingOnBankHolidayValidatorImpl;
import com.luxoft.tradevalidator.validator.impl.ValueDateFallingOnWeekendValidatorImpl;

@RunWith(MockitoJUnitRunner.class)
public abstract class GeneralTradeServiceTest {

	protected abstract ExpectedException getExpectedException();
	
	protected abstract TradeService getTradeService();
	
	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Spy
	private List<GeneralTradeValidator> generalValidators = new ArrayList<>();
	
	@Before
	public void setup() {
		generalValidators.add(new ValueDateBeforeTradeDateValidatorImpl());
		generalValidators.add(new ValueDateFallingOnBankHolidayValidatorImpl(bankHolidayRepository));
		generalValidators.add(new ValueDateFallingOnWeekendValidatorImpl());
		
		MockitoAnnotations.initMocks(bankHolidayRepository);
		BDDMockito.given(bankHolidayRepository.findAll()).willReturn(Collections.emptyList());
	}
	
	
	@Test
	public void testValidateValueDateBeforeTradeDate() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 6));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		getExpectedException().expect(TradeValidationException.class);
		getExpectedException().expectMessage(Matchers.containsString("'Value Date' cannot be before 'Trade Date'"));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		getTradeService().validate(tradeList);
		
	}

	@Test
	public void testValidateValueDateFallingOnSaturday() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 4));//saturday
		tradeData.setTradeDate(LocalDate.of(2018, 8, 1));
		
		getExpectedException().expect(TradeValidationException.class);
		getExpectedException().expectMessage(Matchers.containsString("'Value Date' cannot fall on weekend"));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		getTradeService().validate(tradeList);
		
	}
	
	@Test
	public void testValidateValueDateFallingOnSunday() throws TradeValidationException {
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 5));//sunday
		tradeData.setTradeDate(LocalDate.of(2018, 8, 1));
		
		getExpectedException().expect(TradeValidationException.class);
		getExpectedException().expectMessage(Matchers.containsString("'Value Date' cannot fall on weekend"));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		getTradeService().validate(tradeList);
		
	}
	
	@Test
	public void testValidateValueDateFallingOnNonWorkingDay() throws TradeValidationException {
		
		BankHoliday bh1 = new BankHoliday(1, "EURUSD", 1, 1, "New Year's Day");
		BankHoliday bh2 = new BankHoliday(2, "USDEUR", 1, 5, "Day OFF");
		BankHoliday bh3 = new BankHoliday(2, "USDEUR", 2, 1, "Day OFF");
		
		TradeData tradeData = createDefaultValidTradeData();
		tradeData.setValueDate(LocalDate.of(2018, 1, 1));
		tradeData.setTradeDate(LocalDate.of(2017, 12, 29));//week
		
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(tradeData.getCcyPair())).willReturn(Arrays.asList(bh3, bh2, bh1));
		
		getExpectedException().expect(TradeValidationException.class);
		getExpectedException().expectMessage(Matchers.containsString(
				String.format("'Value Date' cannot fall on Bank Holiday. Day: %s, Month: %s, Description: %s",
				bh1.getDay(), bh1.getMonth(),
				bh1.getDescription())));
		
		List<TradeData> tradeList = Arrays.asList(tradeData);
		
		getTradeService().validate(tradeList);
		
	}
	
	
	private TradeData createDefaultValidTradeData() {
		TradeData tradeData = TradeData.builder()
			.customer(CustomerType.PLUTO1)
			.ccyPair("EURUSD")
			.style(TradeStyle.EUROPEAN)
			.type(TradeType.SPOT)
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


}
