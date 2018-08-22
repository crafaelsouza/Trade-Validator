package com.luxoft.tradevalidator.util.annotations;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.luxoft.tradevalidator.util.annotations.NotNullIfAnotherFieldIsEqualsTo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RunWith(MockitoJUnitRunner.class)
public class NotNullIfAnotherFieldIsEqualsToValidatorTest {

	@Test
	public void testEmpty() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub());
		Assert.assertTrue((constraintViolations).isEmpty());
	}

	@Test
	public void testNoRequiredField() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("Test", null, null));
		Assert.assertTrue((constraintViolations).isEmpty());
	}

	@Test
	public void testWithRequiredField() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("OK", null, null));
		Assert.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<Stub> constraintViolation = constraintViolations.iterator().next();
		Assert.assertTrue(constraintViolation.getMessage().contains("type is required when status is equal to OK"));
		Assert.assertTrue(constraintViolation.getPropertyPath().toString().equals("type"));
	}
	
	@Test
	public void testWithRequiredAndInformedField() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("OK", "Test", null));
		Assert.assertTrue(constraintViolations.isEmpty());
	}
	
	@Test
	public void testWithDifferentValueAndNoRequiredFieldNeed() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("OK1", "Test", null));
		Assert.assertTrue(constraintViolations.isEmpty());
	}
	
	private Validator getValidator() {
		return Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
	}

	@NotNullIfAnotherFieldIsEqualsTo.List({ //
		@NotNullIfAnotherFieldIsEqualsTo(fieldName = "status", fieldValue = "OK", requiredFieldName = "type"), //
		@NotNullIfAnotherFieldIsEqualsTo(fieldName = "type", fieldValue = "TYPE_2", requiredFieldName = "value") })
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    private static class Stub {
		private String status;
		private String type;
		private String value;
	}
}
