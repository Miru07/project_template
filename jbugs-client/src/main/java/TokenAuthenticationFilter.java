import ro.msg.edu.jbugs.type.PermissionType;
import utils.TokenService;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * intercepts all requests from frontend
 *
 */
@Provider
public class TokenAuthenticationFilter implements ContainerRequestFilter {

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

        /*
         ************************************USERS*******************************************
         */
        // GET users
        if(containerRequestContext.getUriInfo().getRequestUri().getPath().endsWith("/jbugs/api/users")
         && containerRequestContext.getMethod().equals("GET")){
            // boolean hasP = TokenService.currentUserHasPermission(token, PermissionType.USER_MANAGEMENT.getActualString());
            return;
            /*
            if(TokenService.currentUserHasPermission(token, PermissionType.USER_MANAGEMENT.getActualString())){
                return;
            }
             */
        }
            // containerRequestContext.abortWith(null);

        String reqpath = containerRequestContext.getUriInfo().getRequestUri().getPath();
        String method = containerRequestContext.getMethod();
        String a = "aaa";
        String b = "bbb";

        MultivaluedMap<String, String> headerss = containerRequestContext.getHeaders();

        String msg = "Filter executed.";
        System.out.println(msg);
    }
}
