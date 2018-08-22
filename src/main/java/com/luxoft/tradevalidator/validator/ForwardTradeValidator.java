package com.luxoft.tradevalidator.validator;

import com.luxoft.tradevalidator.domain.enums.TradeType;

public interface ForwardTradeValidator extends TradeValidator {
	
	public static final TradeType TRADE_TYPE = TradeType.FORWARD;
}
