package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LegalEntityType {

	CS_ZURICH;


	@JsonCreator
    public static LegalEntityType forValue(String value) {
        for (LegalEntityType type : LegalEntityType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Legal Entity. The only acceptable Legal Entities are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return toString();
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < LegalEntityType.values().length; i++) {
			literal.append(LegalEntityType.values()[i].toString());
			if (i + 2 == LegalEntityType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < LegalEntityType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
