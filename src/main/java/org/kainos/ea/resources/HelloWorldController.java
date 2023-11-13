package org.kainos.ea.resources;

import org.kainos.ea.api.HelloWorldService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.HelloWorldDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class HelloWorldController {
    private final HelloWorldService helloWorldService;

    public HelloWorldController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        helloWorldService = new HelloWorldService(databaseConnector, new HelloWorldDao());
    }

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
