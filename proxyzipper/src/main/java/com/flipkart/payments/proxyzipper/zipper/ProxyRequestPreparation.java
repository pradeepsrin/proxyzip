package com.flipkart.payments.proxyzipper.zipper;

import com.flipkart.payments.proxyzipper.config.ZipperConfig;
import com.flipkart.payments.proxyzipper.models.ProxyCallContext;
import com.google.common.annotations.VisibleForTesting;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;

import java.util.function.Function;

public class ProxyRequestPreparation extends MethodBasedRequestBuilder implements Function<ProxyCallContext, BoundRequestBuilder> {


    @VisibleForTesting
    public static Function<ProxyCallContext, String> PREPARE_FQ_URL = callDefinition -> callDefinition.getPath();
    private final ZipperConfig zipperConfig;

    public ProxyRequestPreparation(AsyncHttpClient client, ZipperConfig zipperConfig) {
        super(client);
        this.zipperConfig = zipperConfig;
    }

    @Override
    public BoundRequestBuilder apply(ProxyCallContext context) {
        String url = zipperConfig.getProxyTo() + "/" + PREPARE_FQ_URL.apply(context);
        return baseRequestBuilder(url, context.getHttpMethod());
    }

}
