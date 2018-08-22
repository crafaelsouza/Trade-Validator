package com.luxoft.tradevalidator.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.luxoft.tradevalidator.domain.TradeData;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class TradeErrorCollector {

	private TradeData tradeData;
	private Map<String, List<String>> fieldErrors = new HashMap<>();
	
	public void addError(TradeErrorVO tradeErrorVO) {
		if (fieldErrors.containsKey(tradeErrorVO.getField())) {
			fieldErrors.get(tradeErrorVO.getField()).add(tradeErrorVO.getMessage());
		} else {
			List<String> erros = new ArrayList<String>();
			erros.add(tradeErrorVO.getMessage());
			fieldErrors.put(tradeErrorVO.getField(), erros);
		}
	}
	
	public TradeErrorCollector(TradeData tradeData) {
		this.tradeData = tradeData;
	}
	
	@Override
	public String toString() {
		return "Errors: " + fieldErrors.toString();
	}

	public boolean containsErrors() {
		return !fieldErrors.isEmpty();
	}
	
}