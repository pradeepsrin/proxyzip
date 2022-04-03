package com.flipkart.payments.proxyzipper.zipper;

import com.flipkart.payments.proxyzipper.config.CallDefinition;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;

public class ZipRequestPreparation extends MethodBasedRequestBuilder {

    public ZipRequestPreparation(AsyncHttpClient client) {
        super(client);
    }

    public BoundRequestBuilder apply(CallDefinition callDefinition, String requestBody) {
        BoundRequestBuilder builder = baseRequestBuilder(callDefinition.getBasePath() + callDefinition.getRelativePath(), callDefinition.getHttpMethod());
        builder.setBody(requestBody);
        return builder;
    }
}
