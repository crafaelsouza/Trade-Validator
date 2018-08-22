package com.luxoft.tradevalidator.validator.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.validator.GeneralTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ValueDateFallingOnBankHolidayValidatorImpl implements GeneralTradeValidator {

	private final BankHolidayRepository bankHolidayRepository;
	
	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {
		
		TradeErrorVO error = null;
		List<BankHoliday> bankHolidays = bankHolidayRepository.findByCurrencyPair(tradeData.getCcyPair());
		
		for (BankHoliday bankHoliday : bankHolidays) {
			if (tradeData.getValueDate().getDayOfMonth() == bankHoliday.getDay() &&
				tradeData.getValueDate().getMonthValue() == bankHoliday.getMonth()) {
				
				String msg = String.format("'Value Date' cannot fall on Bank Holiday. Day: %s, Month: %s, Description: %s",
						bankHoliday.getDay(), bankHoliday.getMonth(), bankHoliday.getDescription());
			
				error = new TradeErrorVO("valueDate", msg);
			}
		}
		return Optional.ofNullable(error);
	}

}
