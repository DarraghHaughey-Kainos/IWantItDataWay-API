package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.HelloWorldService;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("I Want It Data Way Auth API")
@Path("/api")
public class AuthController {
    private AuthService authService;

    public AuthController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            authService = new AuthService(databaseConnector, new AuthDao());
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Credential login) {
        try {
            return Response.ok(authService.register(login)).build();
        } catch(ActionFailedException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credential login){
        try {
            String token = authService.login(login);
            return Response.ok(token).build();
        } catch(AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch(ActionFailedException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
