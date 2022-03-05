package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "resilience4j.circuitbreaker")
@Data
public class CircuitBreakerConfigurationProperties {
	private CircuitBreakerAttributesProperties circuitBreakerNumberToWords;
    private CircuitBreakerAttributesProperties circuitBreakerNumberToDollars;
}
