package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.HelloWorldService;
import org.kainos.ea.client.ActionFailedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class HelloWorldController {
    private final HelloWorldService helloWorldService = new HelloWorldService();

    @GET
    @Path("/hello-world")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(helloWorldService.getHelloWorld())
                    .build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }
}
