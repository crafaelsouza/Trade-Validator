package com.luxoft.tradevalidator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.enums.CurrencyPairType;

public interface BankHolidayRepository extends JpaRepository<BankHoliday, Integer>{

	List<BankHoliday> findByCurrencyPair(CurrencyPairType currencyPair);
}
