package com.luxoft.tradevalidator.validator.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.validator.OptionsTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ExcerciseStartDateBeforeTradeDateValidatorImpl implements OptionsTradeValidator {

	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {
		TradeErrorVO error = null;
		if (TradeStyle.AMERICAN.equals(tradeData.getStyle()) && 
				tradeData.getExcerciseStartDate().isBefore(tradeData.getTradeDate())) {
			error = new TradeErrorVO("excerciseStartDate", "'Exercise Start Date' cannot be before 'Trade Date'");
		}
		
		return Optional.ofNullable(error);
	}

}
