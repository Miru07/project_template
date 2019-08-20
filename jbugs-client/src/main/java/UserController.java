import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */

@WebServlet(name = "UserController", urlPatterns = {"/users"})
//@Path("/users")
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

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        UserDTO testUser1 = new UserDTO(0, "test", "test", "077", "email",
                "username", "passw", 0);
        userManagerRemote.insertUser(testUser1);


        List<UserDTO> listOfAllUsers = userManagerRemote.findAllUsers();

        ObjectMapper jsonTransformer = new ObjectMapper();
        String listOfUsersJSON = jsonTransformer.writeValueAsString(listOfAllUsers);

        response.getWriter().append(listOfUsersJSON);
    }
}
