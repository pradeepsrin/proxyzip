package com.flipkart.payments.proxyzipper.resources.upstreamexample;


import com.flipkart.payments.proxyzipper.config.HeaderBasedZipperConfig;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/example")
public class UpstreamExampleResource {

    private final HeaderBasedZipperConfig headerConfig;

    public UpstreamExampleResource(HeaderBasedZipperConfig headerConfig) {
        this.headerConfig = headerConfig;
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/foo")
    public Response foo(@Context HttpHeaders headers) {
        System.out.println("AAPI:" + headers.getHeaderString(headerConfig.getPreCallSensitiveDataMaskedHeader()));
        return Response.ok()
                .header(headerConfig.getPostCallSensitiveDataMaskedHeader(), "PG_PARAM_REF")
                .entity("bar")
                .build();
    }

}
