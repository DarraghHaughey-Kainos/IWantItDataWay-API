package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.SpecificationService;
import org.kainos.ea.client.ActionFailedException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class SpecificationController {

    private final SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @GET
    @Path("/specifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpecifications() {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(specificationService.getAllSpecifications())
                    .build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
