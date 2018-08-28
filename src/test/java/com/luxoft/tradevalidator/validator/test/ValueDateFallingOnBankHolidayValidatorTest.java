package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.validator.impl.ValueDateFallingOnBankHolidayValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateFallingOnBankHolidayValidatorTest {

	@Mock
	private BankHolidayRepository bankHolidayRepository;
	
	@Spy
	@InjectMocks
	private ValueDateFallingOnBankHolidayValidatorImpl validator;
	

	@Test
	public void testValidateValueDateFallingOnBankHoliday(){
		TradeData tradeData = new TradeData();
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 1, 2));
		
		BankHoliday bh1 = new BankHoliday(2, "USDEUR", 1, 5, "Day OFF");
		BankHoliday bh2 = new BankHoliday(3, "USDEUR", 2, 1, "Day OFF");
		
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(tradeData.getCcyPair())).willReturn(Arrays.asList(bh1, bh2));
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateWithoutFallingOnBankHoliday(){
		TradeData tradeData = new TradeData();
		tradeData.setCcyPair("EURUSD");
		tradeData.setValueDate(LocalDate.of(2018, 1, 2));
		
		BankHoliday bh1 = new BankHoliday(1, "EURUSD", 1, 1, "New Year's Day");
		BankHoliday bh2 = new BankHoliday(1, "EURUSD", 2, 5, "New Year's Day");
		
		BDDMockito.given(bankHolidayRepository.findByCurrencyPair(tradeData.getCcyPair())).willReturn(Arrays.asList(bh1, bh2));
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
