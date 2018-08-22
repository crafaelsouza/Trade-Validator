package com.luxoft.tradevalidator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.enums.CurrencyPairType;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;


@Component
public class DatabaseBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
    private BankHolidayRepository bankHolidayRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadBankHoliday();
    }

	private void loadBankHoliday() {
		List<BankHoliday> bankHolidays = new ArrayList<>();
		
		for (CurrencyPairType currency : CurrencyPairType.values()) {
			bankHolidays.add(new BankHoliday(null, currency, 1, 1, "New Year's Day"));
		}
		
		for (CurrencyPairType currency : CurrencyPairType.values()) {
			bankHolidays.add(new BankHoliday(null, currency, 1, 11, "Thanksgiving"));
		}
		
		for (CurrencyPairType currency : CurrencyPairType.values()) {
			bankHolidays.add(new BankHoliday(null, currency, 25, 12, "Christmas"));
		}
		
		for (CurrencyPairType currency : CurrencyPairType.values()) {
			bankHolidays.add(new BankHoliday(null, currency, 31, 12, "New Year's Eve"));
		}
		
		bankHolidays.forEach(b->bankHolidayRepository.save(b));
	}

}
