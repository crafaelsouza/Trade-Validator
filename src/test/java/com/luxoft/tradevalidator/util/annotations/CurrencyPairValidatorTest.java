package com.luxoft.tradevalidator.util.annotations;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyPairValidatorTest {

	@Test
	public void testEmpty() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub());
		Assert.assertTrue((constraintViolations).isEmpty());
	}
	
	@Test
	public void testValidEnum() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("AB"));
		Assert.assertTrue((constraintViolations).isEmpty());
	}
	
	@Test
	public void testEnumWithMoreValues() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("ABC"));
		Assert.assertTrue(!(constraintViolations).isEmpty());
	}
	
	@Test
	public void testEnumWithEqualsValues() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("AA"));
		Assert.assertTrue(!(constraintViolations).isEmpty());
	}
	
	@Test
	public void testEnumFirstValueNonExisting() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("XC"));
		Assert.assertTrue(!(constraintViolations).isEmpty());
	}
	
	@Test
	public void testEnumSecondValueNonExisting() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("AJ"));
		Assert.assertTrue(!(constraintViolations).isEmpty());
	}
	
	@Test
	public void testInvalidEnum1() {
		Validator validator = getValidator();
		Set<ConstraintViolation<Stub>> constraintViolations = validator.validate(new Stub("A"));
		Assert.assertTrue(!(constraintViolations).isEmpty());
	}
	
	private Validator getValidator() {
		return Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
    private static class Stub {
		
		@EnumPairValidator(enumClazz=Status.class, sizeOfEachValue=1)
		private String status;
	}
	
	private enum Status {
		A, B, C;
	}
}
