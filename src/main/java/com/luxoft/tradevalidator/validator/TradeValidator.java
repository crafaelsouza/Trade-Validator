package com.luxoft.tradevalidator.validator;

import java.util.Optional;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

public interface TradeValidator {
	
	Optional<TradeErrorVO> validate(TradeData tradeData);
}
