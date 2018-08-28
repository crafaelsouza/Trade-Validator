package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.validator.impl.ValueDateAbstractValidator;
import com.luxoft.tradevalidator.validator.impl.ValueDateForForwardTransactionValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateForForwardTransactionValidatorTest extends ValueDateAbstractValidatorTest {

	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Mock
	private CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
	@Mock
	private ParameterConfigRepository parameterRepository;
	
	@InjectMocks
	@Spy
	private ValueDateForForwardTransactionValidatorImpl validator;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		super.setup();
		Mockito.reset(bankHolidayRepository, ccPairExceptionRepository, parameterRepository);
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(Mockito.any())).willReturn(Collections.emptyList());
		BDDMockito.given(ccPairExceptionRepository.findAll()).willReturn(Collections.emptyList());
		ParameterConfig param1 = new ParameterConfig(1, ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE, "2");
		ParameterConfig param2 = new ParameterConfig(2, ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE, "1");
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE)).willReturn(param1);
		BDDMockito.given(parameterRepository.findByKey(ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE)).willReturn(param2);
	}

	@Test
	public void testValidateValueDateEqualsSpotDateShortContract(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setTradeDate(LocalDate.of(2018, 8, 6));
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateOneDayAfterSpotDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setTradeDate(LocalDate.of(2018, 8, 6));
		tradeData.setValueDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateOneMonthAfterSpotDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setCcyPair("USDEUR");
		tradeData.setTradeDate(LocalDate.of(2018, 8, 6));
		tradeData.setValueDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
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
