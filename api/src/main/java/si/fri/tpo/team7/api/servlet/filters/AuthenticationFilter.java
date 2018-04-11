package si.fri.tpo.team7.api.servlet.filters;

import org.apache.commons.codec.binary.Base64;
import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.beans.FailedLoginsBean;
import si.fri.tpo.team7.entities.FailedLogin;
import si.fri.tpo.team7.entities.users.User;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.Instant;


//https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey


@Secured
@Provider
@Dependent
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;

    @Inject
    FailedLoginsBean failedLoginsBean;

    @Context
    private HttpServletRequest servletRequest;

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
        String ip = servletRequest.getRemoteAddr();
        if(!failedLoginsBean.allowedToLogin(ip))
            throw new Exception("Too many trials");

        byte[] decoded = Base64.decodeBase64(authenticationToken);
        String[] usernameAndPW = new String(decoded, "UTF-8").split(":");

        if(!validateCredentials(usernameAndPW[0], usernameAndPW[1])){
            throw new Exception("Invalid credentials");
        }else {
            userAuthenticatedEvent.fire(usernameAndPW[0]);
        }
    }

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    private boolean validateCredentials(String username, String password) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        User u = (User) query.getSingleResult();

        if(u.getPassword().equals(password)){
            failedLoginsBean.logSuccessfulLogin(servletRequest.getRemoteAddr());
            return true;
        } else {
            return false;
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        failedLoginsBean.logFailedLogin(servletRequest.getRemoteAddr());
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build());
    }
}