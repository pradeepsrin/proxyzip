package com.flipkart.payments.proxyzipper.config;


import lombok.Data;
import org.eclipse.jetty.http.HttpMethod;

@Data
public class CallDefinition {
    private HttpMethod httpMethod;

    private String relativePath;

    //TODO: Replace with serviceName to avoid duplicates
    private String basePath;

}
