package com.luxoft.tradevalidator.validator.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.CCPairExceptionSpotTrade;
import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.util.DateUtil;
import com.luxoft.tradevalidator.validator.SpotTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public abstract class ValueDateAbstractValidator implements SpotTradeValidator {

	private final ParameterConfigRepository parameterRepository;
	
	private final CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
	private final BankHolidayRepository bankHolidayRepository;
	
	protected abstract Optional<TradeErrorVO> validate(TradeData tradeData, LocalDate minimalDateForSpotTransaction);
	
	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {

		Integer minDays = null;
		
		List<CCPairExceptionSpotTrade> exceptionsCCPairList = ccPairExceptionRepository.findAll();
		
		Set<String> ccPairExceptions = exceptionsCCPairList.stream().map(c->c.getCcyPair()).collect(Collectors.toSet());
		
		ParameterConfig parameterDaysAfterTrade = null;
		
		if (ccPairExceptions.contains(tradeData.getCcyPair().toString())) {
			parameterDaysAfterTrade = parameterRepository.findByKey(ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE);
		} else {
			parameterDaysAfterTrade = parameterRepository.findByKey(ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE);
		}
		
		minDays = Integer.parseInt(parameterDaysAfterTrade.getValue());
		
		LocalDate expectedDay = DateUtil.addWorkingDays(tradeData.getTradeDate(), minDays);
		
		List<BankHoliday> holidays = bankHolidayRepository.findByCurrencyPair(tradeData.getCcyPair());
		
		expectedDay = addNonHolidaysDays(expectedDay, holidays);

		return validate(tradeData, expectedDay);
	}
	
	private LocalDate addNonHolidaysDays(LocalDate expectedDay, List<BankHoliday> holidays) {
		
		while(isHoliday(expectedDay, holidays)) {
			expectedDay = DateUtil.addWorkingDays(expectedDay, 1);
		}
		
		return expectedDay;
	}

	private boolean isHoliday(LocalDate expectedDay, List<BankHoliday> holidays) {
		BankHoliday bankHoliday = null;
		try {
			bankHoliday = holidays.stream()
			.filter(h->h.getDay().equals(expectedDay.getDayOfMonth()) && h.getMonth().equals(expectedDay.getMonthValue()))
			.findFirst().get();
		} catch (NoSuchElementException e) {
			bankHoliday = null;
		}
		return bankHoliday != null;
	}

}
