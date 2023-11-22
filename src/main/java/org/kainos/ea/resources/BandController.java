package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.BandService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.DoesNotExistException;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class BandController {
    private final BandService bandService;
    private final AuthService authService;

    public BandController(BandService bandService, AuthService authService) {

        this.bandService = bandService;
        this.authService = authService;

    }

    @GET
    @Path("/bands")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBands(@HeaderParam("Authorization") String token){
        try {
            String permission = "View";
            authService.isValidToken(token, permission);
            return Response
                    .status(Response.Status.OK)
                    .entity(bandService.getBands())
                    .build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/bands/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBandById(@PathParam("id") int id){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(bandService.getBandById(id))
                    .build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } catch (DoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(HttpStatus.NOT_FOUND_404).build();
        }
    }
}
