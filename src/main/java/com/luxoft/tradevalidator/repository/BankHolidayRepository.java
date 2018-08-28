package com.luxoft.tradevalidator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxoft.tradevalidator.domain.BankHoliday;

public interface BankHolidayRepository extends JpaRepository<BankHoliday, Integer>{

	List<BankHoliday> findByCurrencyPair(String currencyPair);
	
	BankHoliday findByCurrencyPairAndDayAndMonth(String currencyPair, Integer day, Integer month);
	
}
