package com.flipkart.payments.proxyzipper.zipper;

import com.flipkart.payments.proxyzipper.models.ProxyCallContext;
import org.asynchttpclient.BoundRequestBuilder;

import javax.ws.rs.core.Response;
import java.util.Optional;

public interface ZipperHandlingStrategy {

    String getPageKey(ProxyCallContext proxyCallContext);

    void proxyCallResponseAttachSensitiveData(Response.ResponseBuilder responseBuilder, Optional<org.asynchttpclient.Response> postResponse);

    void attachNonSensitiveData(BoundRequestBuilder requestBuilder, Optional<org.asynchttpclient.Response> preResponse);

    String getPreRequestBody(ProxyCallContext context);

    String getPostRequestBody(org.asynchttpclient.Response proxyCallResponse);
}
