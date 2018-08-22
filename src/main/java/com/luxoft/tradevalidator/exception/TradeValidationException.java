package com.luxoft.tradevalidator.exception;

import java.util.List;

import com.luxoft.tradevalidator.vo.TradeErrorCollector;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TradeValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<TradeErrorCollector> tradeValidationList;
	
	public TradeValidationException(List<TradeErrorCollector> tradeValidationList) {
		super(tradeValidationList.toString());
		this.tradeValidationList = tradeValidationList;
	}

	
}
