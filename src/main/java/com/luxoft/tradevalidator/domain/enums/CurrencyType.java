package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {

	EUR,
	USD,
	CAD,
	PHP,
	TRY;
	

	@JsonCreator
    public static CurrencyType forValue(String value) {
        for (CurrencyType type : CurrencyType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Currency. The only acceptable Currencies are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < CurrencyType.values().length; i++) {
			literal.append(CurrencyType.values()[i].toString());
			if (i + 2 == CurrencyType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < CurrencyType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
