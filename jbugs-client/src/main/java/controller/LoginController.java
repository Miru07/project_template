package controller;

import authorization.util.TokenService;
import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginController extends HttpServlet {
    @EJB
    private UserManagerRemote userManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponseUserDTO getUserAndTokenAfterLogin(LoginReceivedDTO loginReceived) throws BusinessException {
        // later on, to be added decrypt password from loginReceived
        // now, it receives password as plain text
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceived);
        if(loginResponseUserDTO.getMessageCode() == LoginResponseUserDTO.SUCCESS) {
            String jwtToken = TokenService.generateLoginToken(loginResponseUserDTO);
            loginResponseUserDTO.setToken(jwtToken);
        }
        return loginResponseUserDTO;
    }
}
