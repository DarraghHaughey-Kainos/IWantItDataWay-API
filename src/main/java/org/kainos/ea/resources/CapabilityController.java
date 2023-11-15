package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class CapabilityController {

    private final CapabilityService capabilityService;

    public CapabilityController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        capabilityService = new CapabilityService(databaseConnector, new CapabilityDao());
    }

    @GET
    @Path("/capabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCapabilities(){
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(capabilityService.getAllCapabilities())
                    .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

}