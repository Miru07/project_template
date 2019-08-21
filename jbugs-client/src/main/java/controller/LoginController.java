package controller;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dto.UserLoginData;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginController extends HttpServlet {
    @EJB
    private UserManagerRemote userManagerRemote;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserLoginData getUserAfterLogin(UserLoginData userLoginData) {
        return userLoginData;
        /*
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(userLoginData.getUsername());

        userDTO.setPassword(userLoginData.getHashedPassword());
        return userDTO;
    */
    }
}
