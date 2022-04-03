package com.flipkart.payments.proxyzipper.resources.upstreamexample;

import com.flipkart.payments.proxyzipper.config.HeaderBasedZipperConfig;
import lombok.extern.java.Log;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/sensitive")
@Log
public class SensitiveServiceResource {

    private final HeaderBasedZipperConfig headerConfig;

    public SensitiveServiceResource(HeaderBasedZipperConfig headerConfig) {
        this.headerConfig = headerConfig;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/save_card")
    public Response foo(byte[] data) {
        return Response.ok()
                .entity("CARD_REF")
                .build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/fetch_pgparams")
    public Response fetchPgParams(byte[] data, @Context HttpHeaders httpHeaders) {
        //TODO: remove sysout
        System.out.println("POST-DATA:" + new String(data));

        return Response.ok()
                .entity("PARAMS_WITH_CARD_CVV")
                .build();
    }

}
