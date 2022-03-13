package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;

import com.webservicesserver.NumberConversionSoapType;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NumberConversionSoapClientConfiguration {

    private final SoapClientProperties soapClientProperties;

    private final Logger LOG = LoggerFactory.getLogger(NumberConversionSoapClientConfiguration.class);

    /**
     * Configurar cliente Soap para cualquier método
     * @return
     */
    @Bean
    public NumberConversionSoapType numberConversionSoapType(){
        // build the client
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(NumberConversionSoapType.class);
        factory.setAddress(soapClientProperties.getEndpoint());
        // add out default interceptors
        List<Interceptor<? extends Message>> interceptorsOut = new ArrayList<Interceptor<? extends Message>>();
        interceptorsOut.add(new LoggingOutInterceptor());
        factory.setOutInterceptors(interceptorsOut);
        List<Interceptor<? extends Message>> interceptorsIn = new ArrayList<Interceptor<? extends Message>>();
        interceptorsIn.add(new LoggingInInterceptor());
        factory.setInInterceptors(interceptorsIn);
        // set read and time out
        NumberConversionSoapType numberConversionSoapType = (NumberConversionSoapType) factory.create();
        Client client = ClientProxy.getClient(numberConversionSoapType);
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(soapClientProperties.getConnectTimeout());
        httpClientPolicy.setReceiveTimeout(soapClientProperties.getReadTimeout());
        httpClientPolicy.setAllowChunking(false);
        conduit.setClient(httpClientPolicy);
        return numberConversionSoapType;
    }
}
