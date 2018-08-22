package com.luxoft.tradevalidator.controller.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.luxoft.tradevalidator.controller.test.config.AutowiredServiceConfig;
import com.luxoft.tradevalidator.handlers.ExceptionHandlerApi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AutowiredServiceConfig.class })
@WebAppConfiguration
@EnableWebMvc
@ComponentScan(basePackages = { "com.luxoft.tradevalidator.controller",
		"com.luxoft.tradevalidator.handlers"})
public abstract class AbstractControllerTest {

	@Autowired
	private ExceptionHandlerApi exceptionHandlerApi;
	
	protected MockMvc mockMvc;
	
	protected abstract void beforeTests();

	protected abstract Object getController();
	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders
	            .standaloneSetup(getController())
	            .setControllerAdvice(exceptionHandlerApi)
	            .build();
		
		beforeTests();
	}
	
}
