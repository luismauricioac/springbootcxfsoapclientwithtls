package com.luismalamoc.springbootcxfsoapclientwithtls.controller

import com.luismalamoc.springbootcxfsoapclientwithtls.fixture.TestBuilder
import com.luismalamoc.springbootcxfsoapclientwithtls.service.NumberConversionService
import org.springframework.http.HttpStatus
import spock.lang.Specification

class NumberConversionControllerSpec extends Specification {

    NumberConversionController controller
    NumberConversionService numberConversionService = Stub()

    def "setup"(){
        controller = new NumberConversionController(numberConversionService)
    }

    def "numberToWords success"() {
        given: "a request"
        when: "call to soap web-service"
        numberConversionService.numberToWords(_) >> TestBuilder.numberToWordsResponseBuilder()
        def response = controller.numberToWords(TestBuilder.numberToWordsRequestBuilder())
        then:
        noExceptionThrown()
        response.statusCode == HttpStatus.OK
    }

    def "numberToDollars success"() {
        given: "a request"
        when: "call to soap web-service"
        numberConversionService.numberToDollars(_) >>  TestBuilder.numberToDollarsResponseBuilder()
        def response = controller.numberToDollars(TestBuilder.numberToDollarsRequestBuilder())
        then:
        noExceptionThrown()
        response.statusCode == HttpStatus.OK
    }
}
