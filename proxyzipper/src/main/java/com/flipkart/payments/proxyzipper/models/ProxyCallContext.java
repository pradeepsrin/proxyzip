package com.flipkart.payments.proxyzipper.models;

import lombok.Builder;
import lombok.Value;
import org.eclipse.jetty.http.HttpMethod;


import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

@Value
@Builder
public class ProxyCallContext {
    private HttpMethod httpMethod;
    private String path;
    private HttpHeaders headers;
    private UriInfo uriInfo;
    private byte[] body;
}
