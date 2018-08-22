package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StrategyType {

	CALL;
	
	@JsonCreator
    public static StrategyType forValue(String value) {
        for (StrategyType type : StrategyType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Strategy Type. The only acceptable Strategies are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < StrategyType.values().length; i++) {
			literal.append(StrategyType.values()[i].toString());
			if (i + 2 == StrategyType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < StrategyType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
