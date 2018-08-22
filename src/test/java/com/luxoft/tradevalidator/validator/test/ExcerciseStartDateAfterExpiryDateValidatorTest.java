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
import com.luxoft.tradevalidator.validator.impl.ExcerciseStartDateAfterExpiryDateValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ExcerciseStartDateAfterExpiryDateValidatorTest {

	@Spy
	private ExcerciseStartDateAfterExpiryDateValidatorImpl validator;
	

	@Test
	public void testValidateExcerciseDateAfterExpiryDate(){
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 10));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateNonAmericanTradeAndExcerciseDateAfterExpiryDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.EUROPEAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 10));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateExcerciseDateEqualsExpiryDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 10));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateExcerciseDateBeforeExpiryDate() {
		TradeData tradeData = new TradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(LocalDate.of(2018, 8, 8));
		tradeData.setExpiryDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
