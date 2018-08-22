package com.luxoft.tradevalidator.util.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.PropertyAccessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotNullIfAnotherFieldIsEqualsToValidator
    implements ConstraintValidator<NotNullIfAnotherFieldIsEqualsTo, Object> {

    private String fieldName;
    private String fieldValue;
    private String requiredFieldName;
    private String message;

    @Override
    public void initialize(NotNullIfAnotherFieldIsEqualsTo annotation) {
        fieldName          = annotation.fieldName();
        requiredFieldName = annotation.requiredFieldName();
        fieldValue = annotation.fieldValue();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
    	try {
	        if (value == null) {
	            return true;
	        }
	
	    	BeanWrapper beanWrapper = new BeanWrapperImpl(value);
	    	
	    	Object valueFieldVerified = beanWrapper.getPropertyValue(fieldName);
	    	if (requiredFieldName.equals("excerciseStartDate")) {
	    		System.out.println("ae");
	    	}
	    	Object valueTargetField = beanWrapper.getPropertyValue(requiredFieldName);
	    	
	        if (valueFieldVerified != null && valueFieldVerified.toString().equals(fieldValue) && valueTargetField == null) {
	        	ctx.disableDefaultConstraintViolation();
	            ctx.buildConstraintViolationWithTemplate(message)
	                .addPropertyNode(requiredFieldName)
	                .addConstraintViolation();
	                return false;
	        }
	
	        return true;
    	}catch (InvalidPropertyException e) {
    		log.error(String.format("FieldNames %s and/or %s was/were not found", fieldName, requiredFieldName));
    		throw e;
    	}catch (PropertyAccessException e) {
    		log.error(String.format("FieldNames %s and/or %s could not be accessed", fieldName, requiredFieldName));
    		throw e;
    	}
    }

}