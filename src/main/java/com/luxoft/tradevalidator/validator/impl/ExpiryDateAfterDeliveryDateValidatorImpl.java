package com.luxoft.tradevalidator.validator.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.validator.OptionsTradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Component
public class ExpiryDateAfterDeliveryDateValidatorImpl implements OptionsTradeValidator {

	@Override
	public Optional<TradeErrorVO> validate(TradeData tradeData) {
		TradeErrorVO error = null;
		if (tradeData.getExpiryDate().isAfter(tradeData.getDeliveryDate())) {
			error = new TradeErrorVO("expiryDate", "'Expiry Date' cannot be after 'Delivery Date'");
		}
		
		return Optional.ofNullable(error);
	}

}
