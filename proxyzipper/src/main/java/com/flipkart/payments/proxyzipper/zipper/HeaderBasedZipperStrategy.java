package com.flipkart.payments.proxyzipper.zipper;

import com.flipkart.payments.proxyzipper.config.HeaderBasedZipperConfig;
import com.flipkart.payments.proxyzipper.models.ProxyCallContext;
import org.asynchttpclient.BoundRequestBuilder;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class HeaderBasedZipperStrategy implements ZipperHandlingStrategy {

    private final HeaderBasedZipperConfig headerConfig;

    public HeaderBasedZipperStrategy(HeaderBasedZipperConfig headerConfig) {
        this.headerConfig = headerConfig;
    }

    public String getPageKey(ProxyCallContext proxyCallContext) {
        return proxyCallContext.getHeaders().getHeaderString(
                headerConfig.getPageKeyHeader()
        ).toLowerCase();
    }

    public void proxyCallResponseAttachSensitiveData(Response.ResponseBuilder responseBuilder, Optional<org.asynchttpclient.Response> postResponse) {
        if ( postResponse.isPresent()) {
            responseBuilder.header(headerConfig.getPostCallSensitiveDataHeader(), postResponse.get().getResponseBody());
        }
    }

    public void attachNonSensitiveData(BoundRequestBuilder requestBuilder, Optional<org.asynchttpclient.Response> preResponse) {
        if ( preResponse.isPresent() ) {
            requestBuilder.setHeader(headerConfig.getPreCallSensitiveDataMaskedHeader(), preResponse.get().getResponseBody());
        }
    }

    public String getPreRequestBody(ProxyCallContext context) {
        return context.getHeaders().getHeaderString(headerConfig.getPreCallSensitiveDataHeader());
    }

    public String getPostRequestBody(org.asynchttpclient.Response proxyCallResponse) {
        return proxyCallResponse.getHeaders().get(headerConfig.getPostCallSensitiveDataMaskedHeader());
    }
}
