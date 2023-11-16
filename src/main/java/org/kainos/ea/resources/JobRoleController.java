package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class JobRoleController {

    private final JobRoleService jobRoleService;

    public JobRoleController(JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoles(){
        try {
            return Response
                        .status(Response.Status.OK)
                        .entity(jobRoleService.getJobRoles())
                        .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Path("/job-role")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJobRole(JobRoleRequest jobRoleRequest){
        try {
            return Response.ok(jobRoleService.createJobRole(jobRoleRequest)).build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
