package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */

@Path("/users")
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
}
