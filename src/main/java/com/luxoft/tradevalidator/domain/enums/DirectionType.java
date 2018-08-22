package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DirectionType {

	BUY,
	SELL;
	

	@JsonCreator
    public static DirectionType forValue(String value) {
        for (DirectionType type : DirectionType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Direction. The only acceptable Direction are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < DirectionType.values().length; i++) {
			literal.append(DirectionType.values()[i].toString());
			if (i + 2 == DirectionType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < DirectionType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
