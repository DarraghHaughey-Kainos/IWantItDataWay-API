package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.HelloWorldService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.HelloWorldDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class HelloWorldController {
    private final HelloWorldService helloWorldService;
    private AuthService authService;

    /**
     * Default constructor catches error for environment variables not set up correctly.
     */
    public HelloWorldController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        helloWorldService = new HelloWorldService(databaseConnector, new HelloWorldDao());
        try {
            authService = new AuthService(databaseConnector, new AuthDao());
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
        }
    }

    @GET
    @Path("/hello-world")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld(@QueryParam("token") String token) {
        try {
            authService.isValidToken(token);

            return Response
                    .status(Response.Status.OK)
                    .entity(helloWorldService.getHelloWorld())
                    .build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}
