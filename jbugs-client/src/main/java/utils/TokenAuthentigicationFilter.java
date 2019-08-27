package utils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

//@Provider
public class TokenAuthentigicationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authHeader = containerRequestContext.getHeaderString("Authorization");
        if (authHeader == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("ERR_MISSING")
                    .build());
            return;
        } else {
            if (!authHeader.contains(" ")) {
                if (authHeader.equals("")) {
                    containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("ERR_FORMAT")
                            .build());
                    return;
                }
            }
        }

        String token = authHeader.split(" ")[1];
        if (TokenService.isTokenExpired(token)){
            containerRequestContext.abortWith(Response.status(Response.Status.GATEWAY_TIMEOUT)
                    .entity("ERR_EXPIRED")
                    .build());
            return;
        }

        // to be added with permission-path links
        // if(TokenService.currentUserHasPermission())
    }
}
