package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.impl.ValueDateFallingOnWeekendValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateFallingOnWeekendValidatorTest {

	
	@Spy
	private ValueDateFallingOnWeekendValidatorImpl validator;
	

	@Test
	public void testValidateValueDateFallingOnSaturday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 4)); //saturdat
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnSunday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 5)); //sunday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnMonday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 6)); //monday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnTuesday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 7)); //tuesday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnWednesday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 8)); //wednesday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnThursday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 9)); //thursday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFallingOnFriday(){
		TradeData tradeData = new TradeData();
		tradeData.setValueDate(LocalDate.of(2018, 8, 10)); //friday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
