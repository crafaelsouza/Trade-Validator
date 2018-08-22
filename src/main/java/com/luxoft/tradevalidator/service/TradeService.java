package com.luxoft.tradevalidator.service;

import java.util.Collection;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.exception.TradeValidationException;

public interface TradeService {

	void validate(Collection<TradeData> tradeDataList) throws TradeValidationException ;
}
