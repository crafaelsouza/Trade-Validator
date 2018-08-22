package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.validator.impl.ExcerciseStartDateBeforeTradeDateValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ExcerciseStartDateBeforeTradeDateValidatorTest {

	@Spy
	private ExcerciseStartDateBeforeTradeDateValidatorImpl validator;
	

	@Test
	public void testValidateExcerciseDateAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateExcerciseDateEqualsTradeDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateExcerciseDateBeforeTradeDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateNonAmericanTradeAndExcerciseDateBeforeTradeDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.EUROPEAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
