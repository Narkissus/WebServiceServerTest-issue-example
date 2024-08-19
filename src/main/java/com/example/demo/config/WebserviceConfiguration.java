package com.example.demo.config;

import org.apache.wss4j.common.ConfigurationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.DelegatingSmartSoapEndpointInterceptor;

import java.util.Map;

@Configuration
public class WebserviceConfiguration {

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(SimplePasswordValidationCallbackHandler callbackHandler) {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setValidationCallbackHandler(callbackHandler);
        interceptor.setValidationActions(ConfigurationConstants.USERNAME_TOKEN);
        return interceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsersMap(Map.of("user", "pass"));
        return callbackHandler;
    }

    @Bean
    public DelegatingSmartSoapEndpointInterceptor delegatingSmartSoapEndpointInterceptor(Wss4jSecurityInterceptor securityInterceptor) {
        return new DelegatingSmartSoapEndpointInterceptor(securityInterceptor);
    }
}
