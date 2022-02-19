package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;

import com.webservicesserver.NumberConversionSoapType;
import com.luismalamoc.springbootcxfsoapclientwithtls.exception.ConfigSoapClientException;
import com.luismalamoc.springbootcxfsoapclientwithtls.handler.PasswordCallbackHandler;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.WSS4JConstants;
import org.apache.xml.security.signature.XMLSignature;
import org.cryptacular.io.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import javax.xml.crypto.dsig.DigestMethod;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

@Component
@RequiredArgsConstructor
public class NumberConversionSoapClientConfiguration {

    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;

    @Value("${server.ssl.key-alias}")
    private String keyStoreAlias;

    @Value("${server.ssl.key-store}")
    private String keyStore;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.trust-store}")
    private String trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    private final SoapClientProperties soapClientProperties;

    private static final String TLSV1_2 = "TLSv1.2";

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
        factory.setBindingId("http://www.w3.org/2003/05/soap/bindings/HTTP/");//soap 1.2
        // add out default interceptors
        List<Interceptor<? extends Message>> interceptorsOut = new ArrayList<Interceptor<? extends Message>>();
        interceptorsOut.add(securityInterceptor());
        interceptorsOut.add(new LoggingOutInterceptor());
        factory.setOutInterceptors(interceptorsOut);
        List<Interceptor<? extends Message>> interceptorsIn = new ArrayList<Interceptor<? extends Message>>();
        interceptorsIn.add(new LoggingInInterceptor());
        factory.setInInterceptors(interceptorsIn);
        // set read and time out
        NumberConversionSoapType numberConversionSoapType = (NumberConversionSoapType) factory.create();
        Client client = ClientProxy.getClient(numberConversionSoapType);
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        conduit.setTlsClientParameters(tlsClientParameters());
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(soapClientProperties.getConnectTimeout());
        httpClientPolicy.setReceiveTimeout(soapClientProperties.getReadTimeout());
        httpClientPolicy.setAllowChunking(false);
        conduit.setClient(httpClientPolicy);
        return numberConversionSoapType;
    }

    /**
     * Configure WSS4JOutInterceptor to do a Digital Sign in the soap message
     */
    @Bean
    public WSS4JOutInterceptor securityInterceptor() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConfigurationConstants.USER, this.keyStoreAlias);
        properties.put(ConfigurationConstants.PW_CALLBACK_REF, new PasswordCallbackHandler());
        properties.put(ConfigurationConstants.ACTION, ConfigurationConstants.SIGNATURE);
        properties.put(ConfigurationConstants.SIG_PROP_REF_ID, "signatureProperties");
        properties.put("signatureProperties", signatureProperties());
        properties.put(ConfigurationConstants.SIG_ALGO, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256);
        properties.put(ConfigurationConstants.SIG_DIGEST_ALGO, DigestMethod.SHA256);
        properties.put(ConfigurationConstants.MUST_UNDERSTAND, "false");
        properties.put(ConfigurationConstants.SIG_C14N_ALGO, WSS4JConstants.C14N_EXCL_WITH_COMMENTS);
        properties.put(ConfigurationConstants.ADD_INCLUSIVE_PREFIXES, "true");
        properties.put(ConfigurationConstants.SIG_KEY_ID, "X509KeyIdentifier");
        properties.put(ConfigurationConstants.USE_SINGLE_CERTIFICATE, "false");
        properties.put(ConfigurationConstants.IS_BSP_COMPLIANT, "false");
        return new WSS4JOutInterceptor(properties);
    }

    /**
     * Configure wss4j's properties
     */
    @Bean
    public Properties signatureProperties(){
        Properties cryptoProperties = new Properties();
        cryptoProperties.put("org.apache.ws.security.crypto.provider",
                "org.apache.ws.security.components.crypto.Merlin");
        cryptoProperties.put("org.apache.wss4j.crypto.merlin.keystore.type", this.keyStoreType);
        cryptoProperties.put("org.apache.wss4j.crypto.merlin.keystore.alias", this.keyStoreAlias);
        cryptoProperties.put("org.apache.wss4j.crypto.merlin.keystore.password", this.keyStorePassword);
        cryptoProperties.put("org.apache.wss4j.crypto.merlin.keystore.private.password", this.keyStorePassword);
        cryptoProperties.put("org.apache.wss4j.crypto.merlin.keystore.file", this.keyStore);
        return cryptoProperties;
    }

    /**
     * Set parameters for TLS, it associates the Truststore and Keystore file to use
     */
    @Bean
    public TLSClientParameters tlsClientParameters() {
        try {
            TLSClientParameters tlsParams = new TLSClientParameters();
            // should be false in production environment
            tlsParams.setDisableCNCheck(soapClientProperties.isDisableHostnameCheck());
            tlsParams.setSecureSocketProtocol(TLSV1_2);
            // truststore
            KeyStore trustStoreVar = KeyStore.getInstance(KeyStore.getDefaultType());
            ClassPathResource classPathResourceTrustStore = new ClassPathResource(this.trustStore);
            InputStream inputStreamTrustStore = classPathResourceTrustStore.getInputStream();
            trustStoreVar.load(inputStreamTrustStore, trustStorePassword.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStoreVar);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();
            tlsParams.setTrustManagers(tm);
            // keystore
            KeyStore keystoreVar = KeyStore.getInstance(this.keyStoreType);
            ClassPathResource classPathResourceKeyStore = new ClassPathResource(this.keyStore);
            InputStream inputStreamKeyStore = classPathResourceKeyStore.getInputStream();
            keystoreVar.load(inputStreamKeyStore, keyStorePassword.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystoreVar, keyStorePassword.toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();
            tlsParams.setKeyManagers(km);

            FiltersType filter = new FiltersType();
            filter.getInclude().add(".*_EXPORT_.*");
            filter.getInclude().add(".*_EXPORT1024_.*");
            filter.getInclude().add(".*_WITH_DES_.*");
            filter.getInclude().add(".*_WITH_NULL_.*");
            filter.getExclude().add(".*_DH_anon_.*");
            tlsParams.setCipherSuitesFilter(filter);

            // important set the SSL context
            SSLContext sslContext = SSLContext.getInstance(TLSV1_2);
            sslContext.init(keyManagerFactory.getKeyManagers() , trustManagerFactory.getTrustManagers(), new SecureRandom());
            tlsParams.setSSLSocketFactory(sslContext.getSocketFactory());

            return tlsParams;
        } catch (Exception ex) {
            LOG.error("[tlsClientParameters][{}][{}]", ex.getClass().getSimpleName(), ex.getMessage(), ex);
            throw new ConfigSoapClientException(ex);
        }
    }
}
