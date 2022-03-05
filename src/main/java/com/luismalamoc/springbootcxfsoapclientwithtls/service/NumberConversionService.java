package com.luismalamoc.springbootcxfsoapclientwithtls.service;

import com.luismalamoc.springbootcxfsoapclientwithtls.client.NumberConversionSoapClient;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsRequest;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsResponse;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsRequest;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class NumberConversionService {

    private final NumberConversionSoapClient numberConversionSoapClient;

    @Autowired
    @Qualifier("circuitBreakerNumberToWords")
    private CircuitBreaker circuitBreakerNumberToWords;

    @Autowired
    @Qualifier("circuitBreakerNumberToDollars")
    private CircuitBreaker circuitBreakerNumberToDollars;

    public NumberToWordsResponse numberToWords(NumberToWordsRequest request){
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreakerNumberToWords,
                        () -> numberConversionSoapClient.numberToWords(request.getUbiNum()));
        String responseSoap = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> {
                    throw new RuntimeException("Something wrong was happened", throwable);
                }).get();
        return NumberToWordsResponse.builder().result(responseSoap).build();
    }

    public NumberToDollarsResponse numberToDollars(NumberToDollarsRequest request){
        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreakerNumberToDollars,
                        () -> numberConversionSoapClient.numberToDollars(request.getDNum()));
        String responseSoap = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> {
                    throw new RuntimeException("Something wrong was happened", throwable);
                }).get();
        return NumberToDollarsResponse.builder().result(responseSoap).build();
    }
}
