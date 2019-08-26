package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */


@Path("/users")
@Produces("application/json")
public class UserController extends HttpServlet {
    @EJB
    private UserManagerRemote userManagerRemote;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() throws JsonProcessingException {
        List<UserDTO> listOfAllUsers = userManagerRemote.findAllUsers();

        ObjectMapper jsonTransformer = new ObjectMapper();
        String listOfUsersJSON = jsonTransformer.writeValueAsString(listOfAllUsers);
        return listOfUsersJSON;
    }


    @POST
    @Path("/create-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserDTO userDTO) throws JsonProcessingException {
        try {
            userManagerRemote.insertUser(userDTO);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

}
