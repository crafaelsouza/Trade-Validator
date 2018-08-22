package com.luxoft.tradevalidator.validator;

import com.luxoft.tradevalidator.domain.enums.TradeType;

public interface OptionsTradeValidator extends TradeValidator {
	
	public static final TradeType TRADE_TYPE = TradeType.OPTIONS; 
	
}
