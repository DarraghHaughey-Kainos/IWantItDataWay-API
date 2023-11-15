package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.BandService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.DatabaseConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class BandController {
    private final BandService bandService;

    public BandController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        bandService = new BandService(databaseConnector, new BandDao());
    }
    @GET
    @Path("/bands")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBands(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(bandService.getBands())
                    .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
