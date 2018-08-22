package com.luxoft.tradevalidator.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luxoft.tradevalidator.controller.TradeController;
import com.luxoft.tradevalidator.domain.TradeData;
import com.luxoft.tradevalidator.domain.enums.CurrencyPairType;
import com.luxoft.tradevalidator.domain.enums.CurrencyType;
import com.luxoft.tradevalidator.domain.enums.CustomerType;
import com.luxoft.tradevalidator.domain.enums.DirectionType;
import com.luxoft.tradevalidator.domain.enums.LegalEntityType;
import com.luxoft.tradevalidator.domain.enums.StrategyType;
import com.luxoft.tradevalidator.domain.enums.TradeStyle;
import com.luxoft.tradevalidator.domain.enums.TradeType;
import com.luxoft.tradevalidator.service.TradeService;
import com.luxoft.tradevalidator.util.LocalDateJsonSerializer;
import com.luxoft.tradevalidator.vo.TradeDataRequestVO;



public class TradeControllerTest extends AbstractControllerTest {

	@Autowired
	private TradeService tradeService;

	@Autowired
	private TradeController tradeController;
	
	private Gson gson;
	
	@Before
	public void setup() {
		gson = new GsonBuilder()
	        .setPrettyPrinting()
	        .registerTypeAdapter(LocalDate.class, new LocalDateJsonSerializer())
	        .create();
	}
	
	@Test
	public void testSpotTradeDataWithCustomerNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setCustomer(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithCCPairNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setCcyPair(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithTypeNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setType(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithDirectionNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setDirection(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithTradeDateNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setTradeDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithAmout1Null() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setAmount1(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithAmout2Null() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setAmount2(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithRateNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setRate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithLegalEntityNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setLegalEntity(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSpotTradeDataWithTraderNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		tradeData.setTrader(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithCustomerNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setCustomer(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithCCPairNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setCcyPair(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithTypeNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setType(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithDirectionNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setDirection(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithTradeDateNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setTradeDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithAmout1Null() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setAmount1(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithAmout2Null() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setAmount2(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithRateNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setRate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithLegalEntityNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setLegalEntity(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testForwardTradeDataWithTraderNull() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		tradeData.setTrader(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithCustomerNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setCustomer(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithCCPairNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setCcyPair(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithStyleNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setStyle(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithTypeNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setType(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithDirectionNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setDirection(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithTradeDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setTradeDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithAmout1Null() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setAmount1(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithAmout2Null() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setAmount2(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithRateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setRate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithLegalEntityNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setLegalEntity(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithTraderNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setTrader(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithStrategyNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setStrategy(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithDeliveryDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setDeliveryDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithExpiryDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setExpiryDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	

	@Test
	public void testOptionsTradeDataWithPayCCNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setPayCC(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithPremiumNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setPremium(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithPremiumCcyNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setPremiumCcy(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithPremiumDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setPremiumDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithAmericanStyleAndExcerciseStartDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setStyle(TradeStyle.AMERICAN);
		tradeData.setExcerciseStartDate(null);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(Arrays.asList(tradeData))))
        		)
    			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testOptionsTradeDataWithEuropeanStyleAndExcerciseStartDateNull() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		tradeData.setStyle(TradeStyle.EUROPEAN);
		tradeData.setExcerciseStartDate(null);
		
		List<TradeData> tradeDataList = Arrays.asList(tradeData);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(tradeDataList)))
        		)
    			.andExpect(status().isOk());
		
		Mockito.verify(tradeService, Mockito.atLeastOnce()).validate(tradeDataList);
	}
	
	@Test
	public void testOptionsTradeDataValid() throws Exception {
		TradeData tradeData = createValidOptionsTradeData();
		
		List<TradeData> tradeDataList = Arrays.asList(tradeData);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(tradeDataList)))
        		)
    			.andExpect(status().isOk());
		
		Mockito.verify(tradeService, Mockito.atLeastOnce()).validate(tradeDataList);
	}
	
	@Test
	public void testForwardTradeDataValid() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.FORWARD);
		
		List<TradeData> tradeDataList = Arrays.asList(tradeData);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(tradeDataList)))
        		)
    			.andExpect(status().isOk());
		
		Mockito.verify(tradeService, Mockito.atLeastOnce()).validate(tradeDataList);
	}
	
	@Test
	public void testSpotTradeDataValid() throws Exception {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.SPOT);
		
		List<TradeData> tradeDataList = Arrays.asList(tradeData);
		
		mockMvc.perform(post("/v1/validator")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new TradeDataRequestVO(tradeDataList)))
        		)
    			.andExpect(status().isOk());
		
		Mockito.verify(tradeService, Mockito.atLeastOnce()).validate(tradeDataList);
	}
	
	
	@Override
	protected void beforeTests() {
		Mockito.reset(tradeService);
	}

	@Override
	protected Object getController() {
		return tradeController;
	}
	
	private TradeData createValidSpotOrForwardTradeData(TradeType type) {
		TradeData tradeData = TradeData.builder()
			.customer(CustomerType.PLUTO1)
			.ccyPair(CurrencyPairType.EURUSD)
			.type(type)
			.direction(DirectionType.BUY)
			.tradeDate(LocalDate.of(2018, 8, 7))
			.amount1(BigDecimal.TEN)
			.amount2(BigDecimal.TEN)
			.rate(2.01D)
			.valueDate(LocalDate.now())
			.legalEntity(LegalEntityType.CS_ZURICH)
			.trader("Johan Baumfiddler")
			.build();
		return tradeData;
	}
	
	private TradeData createValidOptionsTradeData() {
		TradeData tradeData = createValidSpotOrForwardTradeData(TradeType.OPTIONS);
		tradeData.setStrategy(StrategyType.CALL);
		tradeData.setStyle(TradeStyle.EUROPEAN);
		tradeData.setDeliveryDate(LocalDate.now());
		tradeData.setExpiryDate(LocalDate.now());
		tradeData.setPayCC(CurrencyType.USD);
		tradeData.setPremium(2D);
		tradeData.setPremiumCcy(CurrencyType.USD);
		tradeData.setPremiumDate(LocalDate.now());
		tradeData.setExcerciseStartDate(LocalDate.now());
		return tradeData;
	}
	
}
