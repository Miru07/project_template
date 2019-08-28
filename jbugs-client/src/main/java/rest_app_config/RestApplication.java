package rest_app_config;

import rest_app_config.filters.CorsFilter;
import rest_app_config.filters.TokenAuthenticationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

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
        classes.add(TokenAuthenticationFilter.class); // request
        return classes;
    }
     */

}
