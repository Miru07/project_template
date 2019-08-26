package controller;

import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import utils.TokenService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginController extends HttpServlet {
    @EJB
    private UserManagerRemote userManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseUserDTO getUserAndTokenAfterLogin(LoginReceivedDTO loginReceived) {
        // later on, to be added decrypt password from loginReceived
        // now, it receives password as plain text
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceived);
        if(loginResponseUserDTO.getMessageCode() == LoginResponseUserDTO.SUCCESS) {
            String jwtToken = TokenService.generateJbugsToken(loginResponseUserDTO);
            loginResponseUserDTO.setToken(jwtToken);
        }
        return loginResponseUserDTO;
    }
}
