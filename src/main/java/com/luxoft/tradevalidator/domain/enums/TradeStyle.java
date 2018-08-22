package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TradeStyle {

	AMERICAN, 
	EUROPEAN;
	
	@JsonCreator
    public static TradeStyle forValue(String value) {
        for (TradeStyle type : TradeStyle.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Trade Style. The only acceptable Styles are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < TradeStyle.values().length; i++) {
			literal.append(TradeStyle.values()[i].toString());
			if (i + 2 == TradeStyle.values().length) {
				literal.append(" and ");
			} else if (i + 1 < TradeStyle.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
