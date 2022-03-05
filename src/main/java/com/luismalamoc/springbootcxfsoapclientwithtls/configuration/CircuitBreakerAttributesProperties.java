package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;

import lombok.Data;

@Data
public class CircuitBreakerAttributesProperties {

	private float failureRateThreshold;
	private int slidingWindowSize;
	private int minimumNumberOfCalls;
	private int waitDurationInOpenState;
	private int slowCallDurationThreshold;
	private float slowCallRateThreshold;
}
