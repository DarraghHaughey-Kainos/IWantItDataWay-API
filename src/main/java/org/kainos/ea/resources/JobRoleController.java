package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;
import org.kainos.ea.client.AuthenticationException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500;
import static org.eclipse.jetty.http.HttpStatus.UNAUTHORIZED_401;

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
    public Response getJobRoles(@HeaderParam("Authorization") String token) {
        try {
            String permission = "View";
            authService.isValidToken(token, permission);
            return Response
                    .status(Response.Status.OK)
                    .entity(jobRoleService.getJobRoles())
                    .build();
        } catch (ActionFailedException e) {
            System.out.println(e.getMessage());
            return Response.status(INTERNAL_SERVER_ERROR_500).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(UNAUTHORIZED_401).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJobRole(JobRoleRequest jobRoleRequest, @HeaderParam("Authorization") String token) {
        try {
            String permission = "View";
            authService.isValidToken(token, permission);
            return Response.status(CREATED_201).entity(jobRoleService.createJobRole(jobRoleRequest)).build();
        } catch (ActionFailedException e) {
            System.err.println(e.getMessage());
            return Response.status(INTERNAL_SERVER_ERROR_500).build();
        } catch (DoesNotExistException e) {
            System.out.println(e.getMessage());
            return Response.status(NOT_FOUND_404).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(UNAUTHORIZED_401).entity(e.getMessage()).build();
        }
    }
}
