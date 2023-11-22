package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.client.AuthenticationException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("I Want It Data Way API")
@Path("/api")
public class JobRoleController {

    private final JobRoleService jobRoleService;
    private AuthService authService;

    public JobRoleController(JobRoleService jobRoleService, AuthService authService) {
        this.jobRoleService = jobRoleService;
        this.authService = authService;
    }

    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoles(@HeaderParam("Authorization") String token){
        try {
            String permission = "View";
            authService.isValidToken(token, permission);
            return Response
                        .status(Response.Status.OK)
                        .entity(jobRoleService.getJobRoles())
                        .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/job-roles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoleById(@HeaderParam("Authorization") String token, @PathParam("id") int id) {
        try {
            String permission = "View";
            authService.isValidToken(token, permission);
            return Response
                    .status(Response.Status.OK)
                    .entity(jobRoleService.getJobRoleById(id))
                    .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } catch (JobRoleDoesNotExistException e) {
            System.out.println(e.getMessage());
            return  Response.status(HttpStatus.NOT_FOUND_404).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }


}
