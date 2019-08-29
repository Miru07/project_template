package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.RoleManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * REST Controller for {@link Role} and {@link Permission} manipulation.
 *
 * @author Mara Corina
 */
@Path("/roles")
@Produces("application/json")
public class RoleController extends HttpServlet {
    @EJB
    private RoleManagerRemote roleManager;

    /**
     * The Controller consumes an id and returns an array of {@link RoleDTO} Objects that
     * map the corresponding {@link Role} objects for the {@link User} object with the
     * id given from teh database
     *
     * @param id is an {@link Integer}
     * @return a success response containing the {@link PermissionDTO} objects
     * @throws {@link BusinessException} bubbles up to here from {@link RoleManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     */
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


    /**
     * The Controller consumes an {@link PermissionsInsertDTO} object that maps the {@link Role}
     *      object id and the array of {@link PermissionDTO} objects that map the {@link Permission}
     *      objects ready to be persisted
     *
     * @param permissionsReguest is a {@link PermissionsInsertDTO}
     * @return a success response
     * @throws {@link BusinessException} bubbles up to here from {@link RoleManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     */
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
