package com.luxoft.tradevalidator.validator.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.GeneralTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ValueDateBeforeTradeDateValidatorImpl implements GeneralTradeValidator {

	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {
		TradeErrorVO error = null;
		if (tradeData.getValueDate().isBefore(tradeData.getTradeDate())) {
			error = new TradeErrorVO("valueDate", "'Value Date' cannot be before 'Trade Date'");
		}
		return Optional.ofNullable(error);
	}
	
}
