package com.example.demo.endpoint;

import com.example.demo.config.WebserviceConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.ws.test.server.RequestCreators.withSoapEnvelope;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

@WebServiceServerTest
@Import(WebserviceConfiguration.class)
class HelloWorldEndpointTest {

    @Autowired
    MockWebServiceClient mockClient;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void testPresenceOfSecurityInterceptor() {
        assertNotNull(applicationContext.getBean(Wss4jSecurityInterceptor.class));
    }

    @Test
    void testHelloRequest() {
        String payload = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ex="http://example.com/">
                    <soapenv:Header>
                 		<wsse:Security
                 				xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
                 				xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
                 				soapenv:mustUnderstand="1">
                 			<wsse:UsernameToken>
                 				<wsse:Username>user</wsse:Username>
                 				<wsse:Password>pass</wsse:Password>
                 			</wsse:UsernameToken>
                 		</wsse:Security>
                 	</soapenv:Header>
                	<soapenv:Body>
                		<ex:helloRequest>
                			<ex:name>Bob</ex:name>
                		</ex:helloRequest>
                	</soapenv:Body>
                </soapenv:Envelope>
                """;

        String expectedResponse = """
                <helloResponse xmlns="http://example.com/">
                    <message>Hello Bob!</message>
                </helloResponse>
                """;

        mockClient.sendRequest(withSoapEnvelope(new StringSource(payload))).andExpect(payload(new StringSource(expectedResponse)));
    }
}