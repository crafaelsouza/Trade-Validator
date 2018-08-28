package com.luxoft.tradevalidator.util.annotations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumPairValidatorImpl implements ConstraintValidator<EnumPairValidator, String> {
	
	List<String> allCurrenciesType = new ArrayList<>();
	int sizeOfEachValue = 0;
	
	@Override
	public void initialize(EnumPairValidator constraintAnnotation) {
		
		this.sizeOfEachValue = constraintAnnotation.sizeOfEachValue();
		Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
		
		@SuppressWarnings("rawtypes")
		Enum[] enumValArr = enumClass.getEnumConstants();

		for (@SuppressWarnings("rawtypes")
		Enum enumVal : enumValArr) {
			allCurrenciesType.add(enumVal.toString().toUpperCase());
		}
	}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
    	
    	if ( value == null ) {
			return true;
		}
    	
    	if (value.length() != sizeOfEachValue*2) {
    		return false;
    	}
    	
    	String baseCC = value.substring(0, this.sizeOfEachValue);
    	String quoteCC = value.substring(this.sizeOfEachValue, sizeOfEachValue*2);
    	
    	if (baseCC.equals(quoteCC)) {
    		return false;
    	}
    	
    	return allCurrenciesType.contains(baseCC) && allCurrenciesType.contains(quoteCC);
    }

}