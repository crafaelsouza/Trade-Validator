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
public class ObjectDetailedErrorVO {

	private Object object;
	private List<DetailedErrorVO> erros;
	
	
}