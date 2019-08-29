package rest_app_config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Rest App registers providers specifically.
 * If getClasses not implemented, then providers are automatically discovered
 */
@ApplicationPath("/api")
public class RestApplication extends Application {

    /*
     * providers not discovered :/
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();

        // Register my custom providers
        classes.add(CorsFilter.class); // response
        classes.add(AuthorizationFilter.class); // request
        return classes;
    }
     */

}
