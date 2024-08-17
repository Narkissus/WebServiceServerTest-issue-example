package com.example.demo.endpoint;

import com.example.HelloRequest;
import com.example.HelloResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloWorldEndpoint {

    @PayloadRoot(namespace = "http://example.com/", localPart = "helloRequest")
    @ResponsePayload
    public HelloResponse getHello(@RequestPayload HelloRequest request) {
        HelloResponse response = new HelloResponse();
        response.setMessage("Hello %s!".formatted(request.getName()));
        return response;
    }
}
