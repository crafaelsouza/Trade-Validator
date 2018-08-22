package com.luxoft.tradevalidator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.luxoft.tradevalidator.domain.enums.CurrencyPairType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankHoliday {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private CurrencyPairType currencyPair;
	
	private Integer day;
	
	private Integer month;
	
	private String description;
}
