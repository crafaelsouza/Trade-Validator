package com.luxoft.tradevalidator.validator.impl;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.GeneralTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ValueDateFallingOnWeekendValidatorImpl implements GeneralTradeValidator {

	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {
		TradeErrorVO error = null;
		if (tradeData.getValueDate().getDayOfWeek() == DayOfWeek.SATURDAY
				|| tradeData.getValueDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
			error = new TradeErrorVO("valueDate", "'Value Date' cannot fall on weekend");
		}
		return Optional.ofNullable(error);
	}

}
