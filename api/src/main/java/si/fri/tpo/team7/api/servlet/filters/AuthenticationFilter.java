package si.fri.tpo.team7.api.servlet.filters;

import org.apache.commons.codec.binary.Base64;
import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.beans.AuthBean;
import si.fri.tpo.team7.entities.users.User;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/*

byte[] decoded = Base64.decodeBase64(token);
        String[] usernameAndPW = new String(decoded, "UTF-8").split(":");
        authBean.validateCredentials(usernameAndPW[0], usernameAndPW[1]);
 */

@Secured
@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private AuthBean authBean;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String authenticationToken = authorizationHeader.substring(6);
            try {
                handleTokenBasedAuthentication(authenticationToken);
            } catch (Exception e) {
                abortWithUnauthorized(requestContext);
            }
        }

        // Other authentication schemes (such as Basic) could be supported
    }

    private void handleTokenBasedAuthentication(String authenticationToken) throws Exception {

        byte[] decoded = Base64.decodeBase64(authenticationToken);
        String[] usernameAndPW = new String(decoded, "UTF-8").split(":");
        if(!authBean.validateCredentials(usernameAndPW[0], usernameAndPW[1])){
            throw new Exception("Invalid credentials");
        };

    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build());
    }
}