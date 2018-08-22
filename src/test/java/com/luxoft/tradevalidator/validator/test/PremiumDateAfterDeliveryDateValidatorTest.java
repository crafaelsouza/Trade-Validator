package com.luxoft.tradevalidator.validator.test;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.impl.PremiumDateAfterDeliveryDateValidatorImpl;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@RunWith(MockitoJUnitRunner.class)
public class PremiumDateAfterDeliveryDateValidatorTest {

	@Spy
	private PremiumDateAfterDeliveryDateValidatorImpl validator;
	

	@Test
	public void testValidatePremiumDateAfterDeliveryDate(){
		TradeData tradeData = new TradeData();
		tradeData.setPremiumDate(LocalDate.of(2018, 8, 10));
		tradeData.setDeliveryDate(LocalDate.of(2018, 8, 8));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(error.isPresent());
	}

	@Test
	public void testValidatePremiumDateEqualsDeliveryDate(){
		TradeData tradeData = new TradeData();
		tradeData.setPremiumDate(LocalDate.of(2018, 8, 10));
		tradeData.setDeliveryDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
	
	@Test
	public void testValidatePremiumDateBeforeDeliveryDate(){
		TradeData tradeData = new TradeData();
		tradeData.setPremiumDate(LocalDate.of(2018, 8, 8));
		tradeData.setDeliveryDate(LocalDate.of(2018, 8, 10));
		
		Optional<TradeErrorVO> error = validator.validate(tradeData);
		
		Assert.assertTrue(!error.isPresent());
	}
}
