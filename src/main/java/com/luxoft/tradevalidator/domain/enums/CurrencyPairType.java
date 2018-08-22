package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyPairType {

	EURUSD,
	USDEUR;
	

	@JsonCreator
    public static CurrencyPairType forValue(String value) {
        for (CurrencyPairType type : CurrencyPairType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Currency Pair. The only acceptable Currency Pairs are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < CurrencyPairType.values().length; i++) {
			literal.append(CurrencyPairType.values()[i].toString());
			if (i + 2 == CurrencyPairType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < CurrencyPairType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
