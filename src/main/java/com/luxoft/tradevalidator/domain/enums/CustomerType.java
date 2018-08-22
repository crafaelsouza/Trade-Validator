package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerType {

	PLUTO1,
	PLUTO2;

	@JsonCreator
    public static CustomerType forValue(String value) {
        for (CustomerType type : CustomerType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Customer. The only acceptable Customers are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < CustomerType.values().length; i++) {
			literal.append(CustomerType.values()[i].toString());
			if (i + 2 == CustomerType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < CustomerType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
