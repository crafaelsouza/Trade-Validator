package com.luxoft.tradevalidator.validator.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;
import com.luxoft.tradevalidator.validator.ForwardTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ValueDateForForwardTransactionValidatorImpl extends ValueDateAbstractValidator
	implements ForwardTradeValidator {

	public ValueDateForForwardTransactionValidatorImpl(ParameterConfigRepository parameterRepository,
			CCPairExceptionSpotTradeRepository ccPairExceptionRepository, BankHolidayRepository bankHolidayRepository) {
		super(parameterRepository, ccPairExceptionRepository, bankHolidayRepository);
	}
	
	@Override
	protected Optional<TradeErrorVO> validate(TradeData tradeData, LocalDate minimalDateForSpotTransaction) {

		TradeErrorVO error = null;
		
		if (!tradeData.getValueDate().isAfter(minimalDateForSpotTransaction)) {
			int daysDifference = 1 + (int) ChronoUnit.DAYS.between(tradeData.getTradeDate(), minimalDateForSpotTransaction);
			String msg = String.format("'Value Date' must be at least %s days after 'Trade Date' (considering business days and holidays for CCPair %s) for %s transaction", daysDifference, tradeData.getCcyPair(), tradeData.getType());
			error = new TradeErrorVO("valueDate", msg);
		}
		
		return Optional.ofNullable(error);
	}

}
