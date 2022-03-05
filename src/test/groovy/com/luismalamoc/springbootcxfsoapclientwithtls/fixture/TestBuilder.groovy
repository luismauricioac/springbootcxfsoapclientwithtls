package com.luismalamoc.springbootcxfsoapclientwithtls.fixture

import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsRequest
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsResponse
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsRequest
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsResponse

class TestBuilder {

    static numberToWordsRequestBuilder(){
        return NumberToWordsRequest.builder().ubiNum(BigInteger.valueOf(100)).build()
    }

    static numberToDollarsRequestBuilder(){
        return NumberToDollarsRequest.builder().dNum(BigDecimal.valueOf(100.5)).build()
    }

    static numberToDollarsResponseBuilder(){
        return NumberToDollarsResponse.builder().result("any").build()
    }

    static numberToWordsResponseBuilder(){
        return NumberToWordsResponse.builder().result("any").build()
    }
}
