package com.luxoft.tradevalidator.service.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.validator.ForwardTradeValidator;
import com.luxoft.tradevalidator.validator.OptionsTradeValidator;
import com.luxoft.tradevalidator.validator.SpotTradeValidator;
import com.luxoft.tradevalidator.validator.TradeValidator;

@Configuration
public class ValidatorsSetup {

	@Autowired(required=false)
	private List<OptionsTradeValidator> optionsTradeValidator;
	
	@Autowired(required=false)
	private List<SpotTradeValidator> spotTradeValidator;
	
	@Autowired(required=false)
	private List<ForwardTradeValidator> forwardTradeValidator;
	
	
	@Bean
	@Qualifier("validatorMap")
	public Map<TradeType, List<? extends TradeValidator>> validatorMap() {
		
		Map<TradeType, List<? extends TradeValidator>> map = new HashMap<>();
		map.put(OptionsTradeValidator.TRADE_TYPE, Optional.ofNullable(optionsTradeValidator).orElse(Collections.emptyList()));
		map.put(SpotTradeValidator.TRADE_TYPE, Optional.ofNullable(spotTradeValidator).orElse(Collections.emptyList()));
		map.put(ForwardTradeValidator.TRADE_TYPE, Optional.ofNullable(forwardTradeValidator).orElse(Collections.emptyList()));
		
		return map;
	}
}
