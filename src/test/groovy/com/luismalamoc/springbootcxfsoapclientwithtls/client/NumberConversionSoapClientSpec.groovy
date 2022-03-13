package com.luismalamoc.springbootcxfsoapclientwithtls.client

import com.luismalamoc.springbootcxfsoapclientwithtls.configuration.NumberConversionSoapClientConfiguration
import com.luismalamoc.springbootcxfsoapclientwithtls.configuration.SoapClientProperties
import spock.lang.Specification

class NumberConversionSoapClientSpec extends Specification {

    NumberConversionSoapClient client
    NumberConversionSoapClientConfiguration config
    SoapClientProperties soapClientProperties = Stub()

    def "setup"(){
        soapClientProperties.getConnectTimeout() >> 10000
        soapClientProperties.getEndpoint() >> "https://www.dataaccess.com/webservicesserver/NumberConversion.wso?wsdl"
        soapClientProperties.getReadTimeout() >> 10000
        soapClientProperties.getUrlWsdl() >> "https://www.dataaccess.com/webservicesserver/NumberConversion.wso?wsdl"
        soapClientProperties.disableHostnameCheck >> true
        config = new NumberConversionSoapClientConfiguration(soapClientProperties)
        client = new NumberConversionSoapClient(config)
    }

    def "numberToWords success"() {
        given: "a request"
        when: "call to soap web-service"
        def response = client.numberToWords(BigInteger.valueOf(100))
        then:
        noExceptionThrown()
    }

    def "numberToDollars success"() {
        given: "a request"
        when: "call to soap web-service"
        def response = client.numberToDollars(BigDecimal.valueOf(100.5))
        then:
        noExceptionThrown()
    }
}
