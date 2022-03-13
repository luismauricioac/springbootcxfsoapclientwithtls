package com.luismalamoc.springbootcxfsoapclientwithtls.client;

import com.luismalamoc.springbootcxfsoapclientwithtls.configuration.NumberConversionSoapClientConfiguration;
import com.webservicesserver.NumberConversionSoapType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
@AllArgsConstructor
public class NumberConversionSoapClient implements NumberConversionSoapType {

    @Autowired
    private NumberConversionSoapClientConfiguration numberConversionSoapClientConfiguration;

    @Override
    public String numberToWords(BigInteger ubiNum) {
        return numberConversionSoapClientConfiguration.numberConversionSoapType().numberToWords(ubiNum);
    }

    @Override
    public String numberToDollars(BigDecimal dNum) {
        return numberConversionSoapClientConfiguration.numberConversionSoapType().numberToDollars(dNum);
    }
}
