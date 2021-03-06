package si.fri.tpo.team7.api.servlet.filters;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class CorsFilter implements ContainerResponseFilter {

    @Inject
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, HEAD, DELETE");
        if (CorsFilter.class.getResource("CorsFilter.class").toString().substring(0,2).equals("fi")) { // Running from fi(le) (development)
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        } else { // Running from jar (production)
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        }
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            responseContext.getHeaders().add("Access-Control-Allow-Headers", requestContext.getHeaderString("Access-Control-Request-Headers"));
        }
    }
}