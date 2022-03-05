package com.luismalamoc.springbootcxfsoapclientwithtls.service

import com.luismalamoc.springbootcxfsoapclientwithtls.client.NumberConversionSoapClient
import com.luismalamoc.springbootcxfsoapclientwithtls.fixture.TestBuilder
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import spock.lang.Specification

class NumberConversionServiceSpec extends Specification {

    NumberConversionService service
    NumberConversionSoapClient numberConversionSoapClient = Stub()
    CircuitBreaker circuitBreakerNumberToWords = Stub()
    CircuitBreaker circuitBreakerNumberToDollars = Stub()

    def "setup"(){
        service = new NumberConversionService(numberConversionSoapClient)
        service.circuitBreakerNumberToWords = circuitBreakerNumberToWords
        service.circuitBreakerNumberToDollars = circuitBreakerNumberToDollars
    }

    def "numberToWords success"() {
        given: "a request"
        when: "call to soap web-service"
        numberConversionSoapClient.numberToWords(_) >> "any"
        def response = service.numberToWords(TestBuilder.numberToWordsRequestBuilder())
        then:
        noExceptionThrown()
        response.result == "any"
    }

    def "numberToDollars success"() {
        given: "a request"
        when: "call to soap web-service"
        numberConversionSoapClient.numberToDollars(_) >> "any"
        def response = service.numberToDollars(TestBuilder.numberToDollarsRequestBuilder())
        then:
        noExceptionThrown()
        response.result == "any"
    }
}
