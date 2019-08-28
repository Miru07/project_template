import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import ro.msg.edu.jbugs.type.PermissionType;
import utils.TokenService;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 * intercepts all requests from frontend
 *
 */
@Provider
public class TokenAuthenticationFilter implements ContainerRequestFilter {

    @EJB
    UserManagerRemote userManager;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(containerRequestContext.getMethod().equals("OPTIONS")){
            return;
        }

        // LOGIN
        if(containerRequestContext.getUriInfo().getRequestUri().getPath().endsWith("/jbugs/api/login")){
            return; // no auth needed for login
        }

        // get TOKEN from authorization header:
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();

        /**
        ********************following lines are for testing purposes***********************
        * intercept all methods and requst paths
         */
        String reqpath = containerRequestContext.getUriInfo().getRequestUri().getPath();
        String method = containerRequestContext.getMethod();

        MultivaluedMap<String, String> headerss = containerRequestContext.getHeaders();
        /**
         ********************previous lines are for testing purposes***********************
         * intercept all methods and requst paths
         */

        if (TokenService.isTokenExpired(token)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("TOKEN_EXPIRED")
                    .build());
            return;
        }
        /*
         ************************************USERS*******************************************
         */
        // GET users
        if(containerRequestContext.getMethod().equals("GET")
            && containerRequestContext.getUriInfo().getRequestUri().getPath().endsWith("/jbugs/api/users"))
        {
            if(TokenService.currentUserHasPermission(userManager, token, PermissionType.USER_MANAGEMENT.getActualString())){
                return;
            }
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("ACCESS_DENIED")
                    .build());
            return;
        }
        // CREATE user
        if(containerRequestContext.getMethod().equals("POST")
                && containerRequestContext.getUriInfo().getRequestUri().getPath().endsWith("/jbugs/api/users/create-user"))
        {
            if(TokenService.currentUserHasPermission(userManager, token, PermissionType.USER_MANAGEMENT.getActualString())){
                return;
            }
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("ACCESS_DENIED")
                    .build());
            return;
        }

        // for the sake of TESTING, accept any unhandled request
        return;

        /*

        // (do not modify) if none of the previous match, then abort with unauthorized
        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("ACCESS_DENIED")
                .build());
        return;

        */
    }
}
