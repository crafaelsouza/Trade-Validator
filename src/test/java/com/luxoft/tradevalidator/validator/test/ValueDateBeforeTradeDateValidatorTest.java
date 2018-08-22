package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.impl.ValueDateBeforeTradeDateValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateBeforeTradeDateValidatorTest {

	
	@Spy
	private ValueDateBeforeTradeDateValidatorImpl validator;
	

	@Test
	public void testValidateValueDateBeforeTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateEqualsTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 9));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
