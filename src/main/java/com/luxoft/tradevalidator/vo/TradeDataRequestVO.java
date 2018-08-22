package com.luxoft.tradevalidator.vo;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.luxoft.tradevalidator.domain.TradeData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TradeDataRequestVO {

	@NotEmpty
	@Valid
	private List<TradeData> tradeData;
}
