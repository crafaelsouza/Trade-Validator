package com.luxoft.tradevalidator.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.StrategyType;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.util.annotations.EnumPairValidator;
import com.luxoft.tradevalidator.util.annotations.NotNullIfAnotherFieldIsEqualsTo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NotNullIfAnotherFieldIsEqualsTo.List({
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Spot", requiredFieldName="valueDate"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Forward", requiredFieldName="valueDate"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="style"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="strategy"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="deliveryDate"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="expiryDate"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="payCC"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="premium"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="premiumCcy"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="type", fieldValue="Options", requiredFieldName="premiumDate"),
	@NotNullIfAnotherFieldIsEqualsTo(fieldName="style", fieldValue="AMERICAN", requiredFieldName="excerciseStartDate")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeData {

	@NotNull
	private CustomerType customer;
	
	@EnumPairValidator(enumClazz=CurrencyType.class, sizeOfEachValue=3)
	@NotNull
	private String ccyPair;
	
	@NotNull
	private TradeType type;
	
	@NotNull
	private DirectionType direction;
	
	@NotNull
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate tradeDate;
	
	@NotNull
	@ApiModelProperty(example="3200.50")
	@NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount1;
	
	@NotNull
	@ApiModelProperty(example="4200.50")
	@NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount2;
	
	@NotNull
	private Double rate;

	@NotNull
	private LegalEntityType legalEntity;
	
	@NotNull
	private String trader;
	
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate valueDate;
	
	private TradeStyle style;
	
	private StrategyType strategy;
	
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate deliveryDate;
	
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate expiryDate;
	
	private CurrencyType payCC;
	
	private Double premium;
	
	private CurrencyType premiumCcy;
	
	private String premiumType;
	
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate premiumDate;
	
	@ApiModelProperty(example="2018-08-30")
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate excerciseStartDate;
}
