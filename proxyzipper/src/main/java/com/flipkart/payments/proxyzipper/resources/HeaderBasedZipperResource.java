package com.flipkart.payments.proxyzipper.resources;


import com.flipkart.payments.proxyzipper.models.ProxyCallContext;
import com.flipkart.payments.proxyzipper.zipper.Zipper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpMethod;
import org.glassfish.jersey.server.ManagedAsync;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Slf4j
@Path("/zipper/headers")
public class HeaderBasedZipperResource {

    private final Zipper zipper;

    @Inject
    public HeaderBasedZipperResource(Zipper zipper) {
        this.zipper = zipper;
    }

    @HEAD
    @Path(value = "/{path: .*}")
    public Response head(@Context HttpHeaders headers, @Context UriInfo uriInfo,
                       @PathParam("path") String path
    ) throws Exception {
        return zipper.processRequest(
                ProxyCallContext.builder().httpMethod(HttpMethod.HEAD).headers(headers).uriInfo(uriInfo).path(path).build()
        );
    }


    @POST
    @Path("/{path: .*}")
    @Produces("application/json")
    @Consumes("application/json")
//    @ManagedAsync
    public Response post( @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @PathParam("path") String path, byte[] body
    ) throws Exception {
        return zipper.processRequest(
                ProxyCallContext.builder().httpMethod(HttpMethod.POST).headers(headers).uriInfo(uriInfo).path(path).body(body).build()
        );
    }

    @PUT
    @Path("/{path: .*}")
    @Produces("application/json")
    @Consumes("application/json")
//    @ManagedAsync
    public Response put(@Context HttpHeaders headers, @Context UriInfo uriInfo,
                        @PathParam("path") String path, byte[] body
    ) throws Exception {
        return zipper.processRequest(
                ProxyCallContext.builder().httpMethod(HttpMethod.PUT).headers(headers).uriInfo(uriInfo).path(path).body(body).build()
        );
    }

    @GET
    @Path("/{path: .*}")
    @Produces("application/json")
    @Consumes("application/json")
//    @ManagedAsync
    public Response get( @Context HttpHeaders headers, @Context UriInfo uriInfo,
            @PathParam("path") String path
    ) throws Exception {
        return zipper.processRequest(
                ProxyCallContext.builder().httpMethod(HttpMethod.GET).headers(headers).uriInfo(uriInfo).path(path).build()
        );
    }


}
