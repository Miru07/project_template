package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dto.UserUpdateDTO;
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


    /**
     * The Controller returns and array of {@link UserDTO} Objects that
     * map all the {@link User} objects from the database
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() throws JsonProcessingException {
        List<UserDTO> listOfAllUsers = userManagerRemote.findAllUsers();

        ObjectMapper jsonTransformer = new ObjectMapper();
        return jsonTransformer.writeValueAsString(listOfAllUsers);
    }

    /**
     * The Controller consumes a {@link UserDTO} Object that
     *         maps the corresponding {@link User} object data that
     *         will be persisted in the database.
     *
     * @param userDTO is an {@link UserDTO} object
     * @return a success response containing the {@link UserDTO} object that maps the object
     *          persisted
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     */
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

    /**
     * The Controller consumes an id and returns true if the corresponding
     * {@link User} object persisted in the database can be deactivated and false
     * if not.
     *
     * @param id is an {@link Integer}
     * @return a success response containing the {@link Boolean} value
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
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
     * The Controller consumes a JSON and maps its content to the {@link UserUpdateDTO} Object.
     * We pass it to the {@link UserManagerRemote} interface to persist the data.
     *
     * @param userUpdateDTO is an {@link UserDTO} object that wraps a
     *      *                {@link UserDTO} object that contains the updated info
     *      *                     of the {@link User} object that will be updated in the database and a
     *      *                {@link String} object corresponding to the user that realized the update.
     * @return a success response containing the {@link UserDTO} object that maps the updated data
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     */
    @PUT
    @Path("/edit-user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserUpdateDTO userUpdateDTO) {
        try {
            return Response.status(Response.Status.OK).entity(userManagerRemote.updateUser(userUpdateDTO)).build();
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

    /**
     * The Controller consumes a username and returns a list of {@link NotificationDTO} objects
     * sent today
     * corresponding to the user with the given username from the database
     *
     * @param username is a {@link String}
     * @return a success response containing a {@link NotificationDTO} object array
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @GET
    @Path("/{username}/notifications/day")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserTodayNotifications(@PathParam("username") String username) {
        try {
            Set<NotificationDTO> listOfNotifications = userManagerRemote.getUserTodayNotifications(username);

            ObjectMapper jsonTransformer = new ObjectMapper();
            return jsonTransformer.writeValueAsString(listOfNotifications);
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    /**
     * The Controller consumes a username and an id and returns a list of {@link NotificationDTO}
     * objects sent after the notification with the id given
     * corresponding to the user with the given username from the database
     *
     * @param username       is a {@link String}
     * @param idNotification is a {@link Integer}
     * @return a success response containing a {@link NotificationDTO} object array
     * @throws {@link BusinessException} bubbles up to here from {@link UserManagerRemote}
     *                and we return an ERROR response to the client containing the thrown exception
     * @author Mara Corina
     */
    @GET
    @Path("/{username}/notifications/{idNotification}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserNewNotifications(@PathParam("username") String username, @PathParam("idNotification") Integer idNotification) {
        try {
            Set<NotificationDTO> listOfNotifications = userManagerRemote.getUserNewNotificationsById(username, idNotification);

            ObjectMapper jsonTransformer = new ObjectMapper();
            return jsonTransformer.writeValueAsString(listOfNotifications);
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

}
