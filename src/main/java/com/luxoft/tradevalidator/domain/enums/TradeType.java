package com.luxoft.tradevalidator.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TradeType {

	SPOT("Spot"),
	FORWARD("Forward"),
	OPTIONS("Options");
	
	private String tradeName;
	
	TradeType(String tradeName) {
		this.tradeName = tradeName;
	}
	
	@JsonCreator
    public static TradeType forValue(String value) {
        for (TradeType type : TradeType.values()) {
			if (type.toString().equalsIgnoreCase(value)) {
				return type;
			}
		}
        
        throw new IllegalArgumentException(String.format("%s is not a valid Trade Type. The only acceptable Trade Types are: %s", value, getLiteralValues()));
    }

	@JsonValue
	public String toValue() {
		return this.tradeName;
	}

	public String toString() {
		return this.tradeName;
	}
	
	public static String getLiteralValues() {
		StringBuilder literal = new StringBuilder();
		for (int i = 0; i < TradeType.values().length; i++) {
			literal.append(TradeType.values()[i].tradeName);
			if (i + 2 == TradeType.values().length) {
				literal.append(" and ");
			} else if (i + 1 < TradeType.values().length){
				literal.append(", ");
			}
		}
		return literal.toString();
	}
}
