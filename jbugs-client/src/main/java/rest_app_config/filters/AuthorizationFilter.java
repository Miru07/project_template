package rest_app_config.filters;

import authorization.util.TokenService;
import rest_app_config.type.util.RegisteredRequestType;
import rest_app_config.type.util.RequestType;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * intercepts all requests from frontend
 */
@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    @EJB
    UserManagerRemote userManager;

    private static final String ACCESS_DENIED_MESSAGE = "ACCESS_DENIED";
    private static final String TOKEN_EXPIRED_MESSAGE = "TOKEN_EXPIRED";

    private Set<RegisteredRequestType> registeredRequestTypes = new HashSet<>(Arrays.asList(
            RegisteredRequestType.OPTIONS,
            RegisteredRequestType.LOGIN,

            RegisteredRequestType.GET_USERS,
            RegisteredRequestType.CREATE_USER,
            RegisteredRequestType.IS_DEACTIVATABLE_ID,
            RegisteredRequestType.EDIT_USER,
            RegisteredRequestType.GET_USER_ID,

            RegisteredRequestType.GET_BUGS,
            RegisteredRequestType.GET_BUG_ID,
            RegisteredRequestType.CREATE_BUG,
            RegisteredRequestType.UPDATE_BUG_ID,
            RegisteredRequestType.UPDATE_BUG,
            // RegisteredRequestType.CLOSE_BUG, // TODO Miruna?
            // RegisteredRequestType.GET_USER_FOR_BUG, // TODO Miruna?

            RegisteredRequestType.SET_PERMISSIONS,
            RegisteredRequestType.GET_PERMISSIONS
    ));

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        RequestType requestType = new RequestType(containerRequestContext.getMethod(),
                containerRequestContext.getUriInfo().getRequestUri().getPath());

        if (requestType.matches(RegisteredRequestType.OPTIONS)) {
            return;
        }
        if (requestType.matches(RegisteredRequestType.LOGIN)) {
            return; // no auth needed
        }
        if (requestType.matches(RegisteredRequestType.GET_BUG_ID)) {
            return; // no permission needed (notifications)
        }

        // get TOKEN from authorization header:
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();

        if (TokenService.isTokenExpired(token)) {
            abortRequestWithMessage(containerRequestContext, TOKEN_EXPIRED_MESSAGE);
            return;
        }

        if(isRequestPermitted(requestType, token)){
            return;
        }

        abortRequestWithMessage(containerRequestContext, ACCESS_DENIED_MESSAGE);
        return;
//
//        RequestType requestType = new RequestType(containerRequestContext.getMethod(),
//                containerRequestContext.getUriInfo().getRequestUri().getPath());
//
//        if (requestType.matches(RegisteredRequestType.OPTIONS)) {
//            return;
//        }
//        if (requestType.matches(RegisteredRequestType.LOGIN)) {
//            return; // no auth needed
//        }
//
//        // get TOKEN from authorization header:
//        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//        String token = authorizationHeader.substring("Bearer".length()).trim();
//
//        if (TokenService.isTokenExpired(token)) {
//            abortRequestWithMessage(containerRequestContext, TOKEN_EXPIRED_MESSAGE);
//            return;
//        }
//
//        if(isRequestPermitted(requestType, token)){
//            return;
//        }
//
//        abortRequestWithMessage(containerRequestContext, ACCESS_DENIED_MESSAGE);
//        return;
    }

    private boolean isRequestPermitted(RequestType requestType, String token) {
        for (RegisteredRequestType regReq : registeredRequestTypes) {
            if (requestType.matches(regReq)) {
                if(isRequestOPTIONSorLOGINorGETbugID(regReq)) {
                    return true;
                }
                if (isRequestPermitted(token, regReq)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    private void abortRequestWithMessage(ContainerRequestContext containerRequestContext, String msg){
        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(msg)
                .build());
    }
    private boolean isRequestOPTIONSorLOGINorGETbugID(RegisteredRequestType regReq){
        if(regReq == RegisteredRequestType.OPTIONS || regReq == RegisteredRequestType.LOGIN
                || regReq == RegisteredRequestType.GET_BUG_ID) {
            return true;
        }
        return false;
    }
    private boolean isRequestPermitted(String token, RegisteredRequestType regReq){
        if (TokenService.currentUserHasPermission(userManager, token, PermissionType.get(regReq.getPermission()))) {
            return true;
        }
        return false;
    }
}


