package com.luxoft.tradevalidator.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(JsonTypeResolver.class)
@JsonInclude(Include.NON_NULL)
public class ApiErrorVO {

	private Integer errorCode;
	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime timestamp;

	private String message;

	private String debugMessage;

	private List<DetailedErrorVO> subErrors;

	private List<ObjectDetailedErrorVO> subTreeErrors;
	
	public ApiErrorVO() {
		timestamp = LocalDateTime.now();
	}

	public ApiErrorVO(HttpStatus status) {
		this();
		this.status = status;
		this.errorCode = status.value();
	}

	public ApiErrorVO(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.errorCode = status.value();
		this.message = "Erro inesperado.";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiErrorVO(HttpStatus status, String message) {
		this();
		this.errorCode = status.value();
		this.status = status;
		this.message = message;
	}

	public ApiErrorVO(HttpStatus status, String message, Throwable ex) {
		this();
		this.errorCode = status.value();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public void addSubError(DetailedErrorVO subError) {
		if (subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);
	}

	public void addValidationError(String object, String message) {
		addSubError(new DetailedErrorVO(object, message));
	}

	public void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new DetailedErrorVO(object, field, message, rejectedValue));
	}

	public void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
				fieldError.getDefaultMessage());
	}

	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}

	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}

	public void addValidationError(ConstraintViolation<?> constraints) {
		this.addValidationError(constraints.getRootBeanClass().getSimpleName(),
				((PathImpl) constraints.getPropertyPath()).getLeafNode().asString(), constraints.getInvalidValue(),
				constraints.getMessage());
	}

	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(this::addValidationError);
	}

}

class JsonTypeResolver extends TypeIdResolverBase {

	@Override
	public String idFromValue(Object value) {
		return value.getClass().getSimpleName().replace("VO", "");
	}

	@Override
	public String idFromValueAndType(Object value, Class<?> suggestedType) {
		return idFromValue(value);
	}

	@Override
	public JsonTypeInfo.Id getMechanism() {
		return JsonTypeInfo.Id.CUSTOM;
	}
}