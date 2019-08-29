package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

/**
 * REST Controller for User manipulation.
 *
 * @author Mara Corina
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
        return jsonTransformer.writeValueAsString(listOfAllUsers);
    }

    /**
     * The Controller consumes a username and returns a list of {@link NotificationDTO} objects
     * corresponding to the user with the given username from the database
     *
     * @param username is a {@link String}
     * @return a success response containing a {@link NotificationDTO} object array
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @GET
    @Path("/{username}/notifications")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserNotifications(@PathParam("username") String username) {
        try {
            Set<NotificationDTO> listOfNotifications = userManagerRemote.getUserNotifications(username);

            ObjectMapper jsonTransformer = new ObjectMapper();
            return jsonTransformer.writeValueAsString(listOfNotifications);
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @POST
    @Path("/create-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserDTO userDTO) {
        try {
            userManagerRemote.insertUser(userDTO);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

    /**
     * The Controller consumes an id and returns true if the corresponding
     * {@link User} object persisted in the database can be deactivated and false
     * if not.
     *
     * @param id is an {@link Integer}
     * @return a success response containing the {@link Boolean} value
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @GET
    @Path("/is-deactivatable/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isDeactivatable(@PathParam("id") Integer id) {
        try {
            boolean response = userManagerRemote.hasBugsAssigned(id);
            return Response.status(Response.Status.OK).entity(!response).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

    /**
     * The Controller consumes a JSON and maps its content to the {@link UserDTO} Object.
     * We pass it to the {@link UserManagerRemote} interface to persist the data.
     *
     * @param userDTO is an {@link UserDTO} object that contains the data to be
     *                updated for a {@link User} object.
     * @return a success response containing the {@link UserDTO} object that maps the updated data
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @PUT
    @Path("/edit-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserDTO userDTO) {
        try {
            return Response.status(Response.Status.OK).entity(userManagerRemote.updateUser(userDTO)).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

    /**
     * The Controller consumes an id and returns an {@link UserDTO} Object that
     * maps the corresponding {@link User} object data persisted in the database.
     *
     * @param id is an {@link Integer}
     * @return a success response containing the {@link UserDTO} object that maps the object
     * from teh database
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id) {
        try {
            UserDTO userDTO = userManagerRemote.findUser(id);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
        }
    }

}
