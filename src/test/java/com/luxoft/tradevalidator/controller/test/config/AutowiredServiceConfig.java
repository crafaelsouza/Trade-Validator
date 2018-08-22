package com.luxoft.tradevalidator.controller.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luxoft.tradevalidator.service.TradeService;

/**
 * @author Rafael Souza
 * 
 * Classe utilizada para criar mock de serviços.
 * O Serviços de todos os testes de Rest devem ser criados nessa classe.
 */
@Configuration
public class AutowiredServiceConfig {

    @Bean
    public TradeService tradeService() {
        return Mockito.mock(TradeService.class);
    }
    
}

