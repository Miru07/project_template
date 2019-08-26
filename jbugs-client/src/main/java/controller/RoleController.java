package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.RoleManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */


@Path("/roles")
@Produces("application/json")
public class RoleController extends HttpServlet {
    @EJB
    private RoleManagerRemote roleManager;

    @GET
    @Path("/get-permissions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRolePermissions(@PathParam("id") int id) throws JsonProcessingException {
        try {
            Set<PermissionDTO> permissionDTOS = roleManager.getRolePermissions(id);
            ObjectMapper jsonTransformer = new ObjectMapper();
            String listOfRoleDtosJSON = jsonTransformer.writeValueAsString(permissionDTOS);
            return Response.status(Response.Status.OK).entity(listOfRoleDtosJSON).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

    @PUT
    @Path("/set-permissions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setRolePermissions(PermissionsInsertDTO permissionsReguest) {
        try {
            roleManager.setRolePermissions(permissionsReguest);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }
}
