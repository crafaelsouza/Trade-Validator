package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

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
import com.luxoft.tradevalidator.validator.impl.ValueDateForSpotTransactionValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateForSpotTransactionValidatorTest extends ValueDateAbstractValidatorTest {

	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Mock
	private CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
	@Mock
	private ParameterConfigRepository parameterRepository;
	
	@InjectMocks
	@Spy
	private ValueDateForSpotTransactionValidatorImpl validator;

	@Before
	public void setup() {
		super.setup();
		
		MockitoAnnotations.initMocks(bankHolidayRepository);
		MockitoAnnotations.initMocks(ccPairExceptionRepository);
		MockitoAnnotations.initMocks(parameterRepository);
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(Collections.emptyList());
		BDDMockito.given(ccPairExceptionRepository.findAll()).willReturn(Collections.emptyList());
		ParameterConfig param1 = new ParameterConfig(1, ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE, "2");
		ParameterConfig param2 = new ParameterConfig(2, ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE, "1");
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE)).willReturn(param1);
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE)).willReturn(param2);
	}

	@Test
	public void testValidateFallingAfterHolidays(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 14));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		List<BankHoliday> holidays = Arrays.asList(new BankHoliday(1, "USDEUR", 13, 8, "Day OFF"));
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(holidays );
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	

	@Test
	public void testValidateValueDateTwoBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateCurrencyPairInExceptionList(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		BDDMockito.given(getCcPairExceptionRepository().findAll()).willReturn(Arrays.asList(new CCPairExceptionSpotTrade(1, "USDEUR")));
		
		Optional<TradeErrorVO> error = getValidator().validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}

	@Override
	protected ValueDateAbstractValidator getValidator() {
		return this.validator;
	}

	@Override
	protected BankHolidayRepository getBankHolidayRepository() {
		return bankHolidayRepository;
	}

	@Override
	protected CCPairExceptionSpotTradeRepository getCcPairExceptionRepository() {
		return ccPairExceptionRepository;
	}

	@Override
	protected ParameterConfigRepository getParameterRepository() {
		return parameterRepository;
	}	
}
