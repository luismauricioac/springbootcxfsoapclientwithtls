package com.luismalamoc.springbootcxfsoapclientwithtls.handler;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

public class PasswordCallbackHandler implements CallbackHandler {

    private String password;

    public PasswordCallbackHandler(String password){
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
                pc.setPassword(this.password);
            } else {
                throw new UnsupportedCallbackException(callbacks[i],
                        "Callback is not encountered");
            }
        }
    }
}
