import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
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

        // String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        //String token = authorizationHeader.substring("Bearer".length()).trim();

        String method = containerRequestContext.getMethod();
        MultivaluedMap<String, String> headerss = containerRequestContext.getHeaders();

        String msg = "Filter executed.";
        System.out.println(msg);
    }
}
