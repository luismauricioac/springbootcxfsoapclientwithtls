package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

	@Autowired
	private CircuitBreakerConfigurationProperties properties;

	@Bean
	@Qualifier("circuitBreakerNumberToWords")
	public CircuitBreaker circuitBreakerNumberToWords(){
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.failureRateThreshold(properties.getCircuitBreakerNumberToWords().getFailureRateThreshold())
				.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
				.slidingWindowSize(properties.getCircuitBreakerNumberToWords().getSlidingWindowSize())
				.minimumNumberOfCalls(properties.getCircuitBreakerNumberToWords().getMinimumNumberOfCalls())
				.waitDurationInOpenState(Duration.ofSeconds(properties.getCircuitBreakerNumberToWords().getWaitDurationInOpenState()))
				.slowCallDurationThreshold(Duration.ofSeconds(properties.getCircuitBreakerNumberToWords().getSlowCallDurationThreshold()))
				.slowCallRateThreshold(properties.getCircuitBreakerNumberToWords().getSlowCallRateThreshold())
				.build();

		CircuitBreakerRegistry circuitBreakerRegistry =
				CircuitBreakerRegistry.of(circuitBreakerConfig);

		return circuitBreakerRegistry.circuitBreaker("circuitBreakerNumberToWords");
	}

    @Bean
    @Qualifier("circuitBreakerNumberToDollars")
    public CircuitBreaker circuitBreakerNumberToDollars(){
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(properties.getCircuitBreakerNumberToDollars().getFailureRateThreshold())
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(properties.getCircuitBreakerNumberToDollars().getSlidingWindowSize())
                .minimumNumberOfCalls(properties.getCircuitBreakerNumberToDollars().getMinimumNumberOfCalls())
                .waitDurationInOpenState(Duration.ofSeconds(properties.getCircuitBreakerNumberToDollars().getWaitDurationInOpenState()))
                .slowCallDurationThreshold(Duration.ofSeconds(properties.getCircuitBreakerNumberToDollars().getSlowCallDurationThreshold()))
                .slowCallRateThreshold(properties.getCircuitBreakerNumberToDollars().getSlowCallRateThreshold())
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        return circuitBreakerRegistry.circuitBreaker("circuitBreakerNumberToDollars");
    }

}
