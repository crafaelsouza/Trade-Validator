package com.luxoft.tradevalidator.util.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullIfAnotherFieldIsEqualsToValidator.class)
@Documented
public @interface NotNullIfAnotherFieldIsEqualsTo {

    String fieldName();
    String fieldValue();
    String requiredFieldName();

    String message() default "{requiredFieldName} is required when {fieldName} is equal to {fieldValue}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNullIfAnotherFieldIsEqualsTo[] value();
    }

}