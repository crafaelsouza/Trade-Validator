package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.validator.impl.ValueDateForSpotTransactionsValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateForSpotTransactionsValidatorTest {

	
	@Spy
	private ValueDateForSpotTransactionsValidatorImpl validator;
	

	@Test
	public void testValidateValueDateBeforeTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 8));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateEqualsTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 9));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateOneDayAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateFalingOnWeekendAndTwoDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 11)); //saturday
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9)); //thursday
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateValueDateTwoBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 13));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidatecCADCurrencyValueDateOneBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.CAD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidatecCADCurrencyValueDateTwoBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.CAD);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidateTRYCurrencyValueDateOneBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.TRY);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidateTRYCurrencyValueDateTwoBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.TRY);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
	
	@Test
	public void testValidatePHPCurrencyValueDateOneBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.PHP);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 9));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidatePHPCurrencyValueDateTwoBusinessDaysAfterTradeDate(){
		TradeData tradeData = new TradeData();
		tradeData.setType(TradeType.SPOT);
		tradeData.setPayCC(CurrencyType.PHP);
		tradeData.setValueDate(LocalDate.of(2018, 8, 10));
		tradeData.setTradeDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}
}
