package com.luxoft.tradevalidator.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DetailedErrorVO {

	private String object;
	private String field;
	private String message;
	private Object rejectedValue;
	private List<String> messages;

	
	public DetailedErrorVO(String object, String message) {
		this.object = object;
		this.message = message;
	}


	public DetailedErrorVO(String field, String message, Object rejectedValue) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}

	public DetailedErrorVO(String field, List<String> messages) {
		super();
		this.field = field;
		this.messages = messages;
	}


	public DetailedErrorVO(String object, String field, String message, Object rejectedValue) {
		super();
		this.object = object;
		this.field = field;
		this.message = message;
		this.rejectedValue = rejectedValue;
	}
	
	
}