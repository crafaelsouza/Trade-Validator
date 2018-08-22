package com.luxoft.tradevalidator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxoft.tradevalidator.exception.TradeValidationException;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.vo.TradeDataRequestVO;

@RestController
@RequestMapping("/v1/validator")
public class TradeController {

	@Autowired
	private TradeService tradeService;
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TradeDataRequestVO> validate(@Validated @RequestBody TradeDataRequestVO tradeRequest) throws TradeValidationException {
		
		tradeService.validate(tradeRequest.getTradeData());
		
		return new ResponseEntity<TradeDataRequestVO>(HttpStatus.OK);
		
	}
}
