package com.luismalamoc.springbootcxfsoapclientwithtls.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class SoapClientProperties {
    private String urlWsdl;
    private int readTimeout;
    private int connectTimeout;
    private String endpoint;
    private boolean disableHostnameCheck;
}
