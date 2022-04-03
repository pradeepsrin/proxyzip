package com.flipkart.payments.proxyzipper.zipper;


import com.flipkart.payments.proxyzipper.config.ZipperConfig;
import com.flipkart.payments.proxyzipper.config.HeaderBasedZipperConfig;
import com.flipkart.payments.proxyzipper.config.ProxyPipelineConfig;
import com.flipkart.payments.proxyzipper.models.ProxyCallContext;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.NotImplementedException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;

import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

public class Zipper {

    private final AsyncHttpClient client;

    private final ImmutableMap<String, ProxyPipelineConfig> pageKeyZipMap;
    private final ZipperConfig zipperConfig;
    private final HeaderBasedZipperStrategy zipperHandlingStrategy;

    public Zipper(ImmutableList<ProxyPipelineConfig> zips, ZipperConfig zipperConfig) {
        this.zipperConfig = zipperConfig;
        client = asyncHttpClient(
                config().setConnectTimeout(5000)
        );

        if ( zipperConfig instanceof HeaderBasedZipperConfig ) {
            this.zipperHandlingStrategy = new HeaderBasedZipperStrategy((HeaderBasedZipperConfig) zipperConfig);
        } else {
            throw new NotImplementedException("Unknown strategy for Zipper");
        }

        _validateZips(zips);
        this.pageKeyZipMap = convertZipListToZipMap.apply(zips);
    }

    @VisibleForTesting
    static Function<ImmutableList<ProxyPipelineConfig>, ImmutableMap<String, ProxyPipelineConfig>> convertZipListToZipMap = proxyZips -> {

        final ImmutableMap.Builder<String, ProxyPipelineConfig> pageKeyZipMap = ImmutableMap.builder();

        proxyZips.stream().iterator().forEachRemaining(proxyZip -> {
            pageKeyZipMap.put(proxyZip.getPageKey().toLowerCase(), proxyZip);
        });

        return pageKeyZipMap.build();
    };

    private void _validateZips(ImmutableList<ProxyPipelineConfig> zips) {
        final Set<String> pageIds = Sets.newConcurrentHashSet();
        zips.stream().iterator().forEachRemaining(proxyZip -> {
            if ( pageIds.contains(proxyZip.getPageKey())) {
                throw new RuntimeException("Duplicate PageKey found in the zips config for : " + proxyZip.getPageKey());
            }
        });
    }


    /*TODO:
       - Modularize
       - make this non-blocking
       - handle the right errors & edge cases
       - build necessary resilience strategies & failure handling of pre post
       - write tests
    */
    public Response processRequest(ProxyCallContext context) throws Exception {
        String pageKey = zipperHandlingStrategy.getPageKey(context);

        Optional<ProxyPipelineConfig> pipelineConfig =
                ((pageKeyZipMap.containsKey(pageKey)) ? Optional.of(pageKeyZipMap.get(pageKey)) : Optional.empty());

        Optional<org.asynchttpclient.Response> preResponse = Optional.empty();
        if ( pipelineConfig.isPresent() && pipelineConfig.get().getPre() != null ) {
            //TODO: Handle GET calls differently as they dont have requestBody. Assuming POST/PUT for now
            String requestBody = zipperHandlingStrategy.getPreRequestBody(context);
            BoundRequestBuilder preRequestBuilder = new ZipRequestPreparation(client).apply(pipelineConfig.get().getPre(), requestBody);

            preResponse = Optional.of(preRequestBuilder.execute().get());
        }

        BoundRequestBuilder requestBuilder = new ProxyRequestPreparation(client, zipperConfig).apply(context);
        zipperHandlingStrategy.attachNonSensitiveData(requestBuilder, preResponse);
        org.asynchttpclient.Response proxyCallResponse = requestBuilder.execute().get();

        Optional<org.asynchttpclient.Response> postResponse = Optional.empty();
        if ( pipelineConfig.isPresent() && pipelineConfig.get().getPre() != null ) {
            //TODO: Handle GET calls differently as they dont have requestBody. Assuming POST/PUT for now
            String requestBody = zipperHandlingStrategy.getPostRequestBody(proxyCallResponse);
            BoundRequestBuilder postRequestBuilder = new ZipRequestPreparation(client).apply(pipelineConfig.get().getPost(), requestBody);
            postResponse = Optional.of(postRequestBuilder.execute().get());
        }

        Response.ResponseBuilder responseBuilder = Response.ok().
                entity(proxyCallResponse.getResponseBody());

        zipperHandlingStrategy.proxyCallResponseAttachSensitiveData(responseBuilder, postResponse);

        return responseBuilder.build();
    }

}
