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
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceived);
        if(loginResponseUserDTO.getMessage() == LoginResponseUserDTO.SUCCESS) {
            String jwtToken = TokenService.generateJWT(loginResponseUserDTO.getId().toString(), "server",
                    loginResponseUserDTO.getUsername(), 123456789);
            loginResponseUserDTO.setToken(jwtToken);
        }
        return loginResponseUserDTO;
    }
}
