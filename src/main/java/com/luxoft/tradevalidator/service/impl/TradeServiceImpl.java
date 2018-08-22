package com.luxoft.tradevalidator.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.validator.GeneralTradeValidator;
import com.luxoft.tradevalidator.validator.TradeValidator;
import com.luxoft.tradevalidator.vo.TradeErrorCollector;
import com.luxoft.tradevalidator.vo.TradeErrorVO;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired(required=false)
	private List<GeneralTradeValidator> generalValidators;
	
	/** @See ValidatorsSetup */
	@Autowired(required=false)
	@Qualifier("validatorMap")
	private Map<TradeType, List<? extends TradeValidator>> specificValidators;
	
	public void validate(Collection<TradeData> tradeDataList) throws TradeValidationException {
		
		List<TradeErrorCollector> allTradeErrors = new LinkedList<>();
		
		tradeDataList.forEach(tradeData -> {
			
			TradeErrorCollector tradeErrorCollector = new TradeErrorCollector(tradeData);
			
			List<TradeValidator> allValidators = getAllValidators(tradeData.getType());
			
			allValidators.forEach(v-> {
					
				Optional<TradeErrorVO> errorVO = v.validate(tradeData);
				
				errorVO.ifPresent(e->tradeErrorCollector.addError(e));
				
			});
			
			if (tradeErrorCollector.containsErrors()) {
				allTradeErrors.add(tradeErrorCollector);
			}
		});
		
		if (!allTradeErrors.isEmpty()) {
			throw new TradeValidationException(allTradeErrors);
		}
	}
	
	private List<TradeValidator> getAllValidators(TradeType tradeType) {
		
		List<TradeValidator> allValidators = new LinkedList<>();
		Optional.ofNullable(generalValidators).ifPresent(v->allValidators.addAll(v));
		
		Optional.ofNullable(specificValidators).ifPresent(v-> {
			if (v.containsKey(tradeType)) {
				allValidators.addAll(v.get(tradeType));
			}
		});
		return allValidators;
	}

}
