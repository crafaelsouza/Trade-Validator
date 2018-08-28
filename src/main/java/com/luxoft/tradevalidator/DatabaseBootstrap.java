package com.luxoft.tradevalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.luxoft.tradevalidator.domain.BankHoliday;
import com.luxoft.tradevalidator.domain.CCPairExceptionSpotTrade;
import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;
import com.luxoft.tradevalidator.repository.BankHolidayRepository;
import com.luxoft.tradevalidator.repository.CCPairExceptionSpotTradeRepository;
import com.luxoft.tradevalidator.repository.ParameterConfigRepository;


@Component
public class DatabaseBootstrap implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
    private BankHolidayRepository bankHolidayRepository;

	@Autowired
    private ParameterConfigRepository parameterRepository;
	
	@Autowired
	private CCPairExceptionSpotTradeRepository ccPairExceptionRepository;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadBankHoliday();
        loadDefaultParameters();
        loadExceptionsForSpotTrade();
    }

	private void loadExceptionsForSpotTrade() {
		CCPairExceptionSpotTrade cc1 = new CCPairExceptionSpotTrade(null, "USDCAD");
		ccPairExceptionRepository.save(cc1);
		
	}

	private void loadDefaultParameters() {
		ParameterConfig param1 = new ParameterConfig(null, ParameterKey.DEFAULT_DAYS_AFTER_TRADE_DATE, "2");
		ParameterConfig param2 = new ParameterConfig(null, ParameterKey.EXCEPTION_DAYS_AFTER_TRADE_DATE, "1");
		
		parameterRepository.save(param1);
		parameterRepository.save(param2);
	}

	private void loadBankHoliday() {
		for (int i = 0; i < CurrencyType.values().length -1; i++) {
			for (int j = i+1; j < CurrencyType.values().length; j++) {
				BankHoliday b1 = new BankHoliday(null, CurrencyType.values()[i].toString()+CurrencyType.values()[j].toString(), 1, 1, "New Years Day");
				BankHoliday b2 = new BankHoliday(null, CurrencyType.values()[i].toString()+CurrencyType.values()[j].toString(), 25, 12, "Christmas Day");
				
				bankHolidayRepository.save(b1);
				bankHolidayRepository.save(b2);
			}
		}
	}

}
