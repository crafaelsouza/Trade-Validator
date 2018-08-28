package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.CCPairExceptionSpotTrade;
import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.validator.impl.ValueDateAbstractValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

public abstract class ValueDateAbstractValidatorTest {

	protected abstract BankHolidayRepository getBankHolidayRepository();
	
	protected abstract CCPairExceptionSpotTradeRepository getCcPairExceptionRepository();
	
	protected abstract ParameterConfigRepository getParameterRepository();
	
	protected abstract ValueDateAbstractValidator getValidator();

	@Before
	public void setup() {
		BDDMockito.given(getBankHolidayRepository().findByCurrencyPair("EURUSD")).willReturn(Collections.emptyList());
		BDDMockito.given(getCcPairExceptionRepository().findAll()).willReturn(Collections.emptyList());
		ParameterConfig param1 = new ParameterConfig(1, ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE, "2");
		ParameterConfig param2 = new ParameterConfig(2, ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE, "1");
		BDDMockito.given(getParameterRepository().findByKey(ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE)).willReturn(param1);
		BDDMockito.given(getParameterRepository().findByKey(ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE)).willReturn(param2);
		MockitoAnnotations.initMocks(getValidator());
	}

	@Test
	public void testValidateValueDateBeforeTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateEqualsTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 9));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateOneDayAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	
	@Test
	public void testValidateValueDateFalingOnWeekendAndTwoDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 11)); //saturday
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9)); //thursday
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateFallingOnHolidays(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		List<BankHoliday> holidays = Arrays.asList(new BankHoliday(1, "USDEUR", 13, 8, "Day OFF"));
		
		BDDMockito.given(getBankHolidayRepository().findByCurrencyPair("USDEUR")).willReturn(holidays );
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
}
