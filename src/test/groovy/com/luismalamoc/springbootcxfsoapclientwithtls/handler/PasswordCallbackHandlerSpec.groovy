package com.luismalamoc.springbootcxfsoapclientwithtls.handler

import spock.lang.Specification

import javax.security.auth.callback.NameCallback
import org.apache.wss4j.common.ext.WSPasswordCallback

import javax.security.auth.callback.UnsupportedCallbackException

class PasswordCallbackHandlerSpec extends Specification {

    PasswordCallbackHandler handler

    def "setup"(){
        handler = new PasswordCallbackHandler("passwd")
    }

    def "handle UnsupportedCallbackException"() {
        given:
        when:
        def cb = new NameCallback("any")
        handler.handle([cb] as NameCallback[])
        then:
        thrown(UnsupportedCallbackException)
    }

    def "handle ok"() {
        given:
        when:
        def cb = new WSPasswordCallback("any", 3)
        handler.handle([cb] as WSPasswordCallback[])
        then:
        noExceptionThrown()
    }
}
