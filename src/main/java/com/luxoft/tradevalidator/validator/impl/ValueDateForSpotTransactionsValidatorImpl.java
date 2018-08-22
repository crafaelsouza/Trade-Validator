package com.luxoft.tradevalidator.validator.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.util.DateUtil;
import com.luxoft.tradevalidator.validator.SpotTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ValueDateForSpotTransactionsValidatorImpl implements SpotTradeValidator {

	private List<CurrencyType> currenciesExceptions;

	private static final Integer MIN_DAYS_BETWEEN_VALUE_DATE_AND_TRADE_DATE = 2;
	
	private static final Integer EXCEPTION_MIN_DAYS_BETWEEN_VALUE_DATE_AND_TRADE_DATE = 1;
	
	public ValueDateForSpotTransactionsValidatorImpl() {
		super();
		//load from db
		currenciesExceptions = new ArrayList<>();
		currenciesExceptions.add(CurrencyType.CAD);
		currenciesExceptions.add(CurrencyType.PHP);
		currenciesExceptions.add(CurrencyType.TRY);
	}


	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {

		TradeErrorVO error = null;

		int minDays = MIN_DAYS_BETWEEN_VALUE_DATE_AND_TRADE_DATE;
		
		if (currenciesExceptions.contains(tradeData.getPayCC())) {
			minDays = EXCEPTION_MIN_DAYS_BETWEEN_VALUE_DATE_AND_TRADE_DATE;
		}
		
		if (!isValidDayDifference(tradeData.getTradeDate(), tradeData.getValueDate(), minDays)) {
			String msg = String.format("'Value Date' must be %s business days after 'Trade Date' for %s transaction", minDays, tradeData.getType());
			error = new TradeErrorVO("valueDate", msg);
		}
		
			
		return Optional.ofNullable(error);
	}
	
	private boolean isValidDayDifference(LocalDate date1, LocalDate date2, int minDifference) {
		LocalDate dateAfter = DateUtil.addWorkingDays(date1, minDifference);
		int daysDifference = (int) ChronoUnit.DAYS.between(date2, dateAfter);
		return daysDifference == 0;
	}
	
}
