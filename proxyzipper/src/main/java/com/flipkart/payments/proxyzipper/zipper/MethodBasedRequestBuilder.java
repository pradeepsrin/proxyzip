package com.flipkart.payments.proxyzipper.zipper;

import org.apache.commons.lang3.NotImplementedException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.eclipse.jetty.http.HttpMethod;

public abstract class MethodBasedRequestBuilder {
    protected final AsyncHttpClient client;

    protected MethodBasedRequestBuilder(AsyncHttpClient client) {
        this.client = client;
    }

    protected BoundRequestBuilder baseRequestBuilder(String url, HttpMethod httpMethod) {
        BoundRequestBuilder reqBuilder = getRawRequestBuilder(url, httpMethod);
        reqBuilder.setHeader("Content-type", "application/json");
        return reqBuilder;
    }

    private BoundRequestBuilder getRawRequestBuilder(String url, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return client.prepareGet(url);
            case POST:
                return client.preparePost(url);
            case PUT:
                return client.preparePut(url);
            case HEAD:
                return client.prepareHead(url);
            default:
                throw new NotImplementedException("Not implemented for HttpMethod " + httpMethod);
        }
    }

}
