package com.luxoft.tradevalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luxoft.tradevalidator.domain.ParameterConfig;
import com.luxoft.tradevalidator.domain.enums.ParameterKey;

public interface ParameterConfigRepository extends JpaRepository<ParameterConfig, Integer>{

	ParameterConfig findByKey(ParameterKey parameterKey);
	
}
