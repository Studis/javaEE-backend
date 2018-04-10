package si.fri.tpo.team7.api.servlet.producers;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.entities.users.User;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
    To get authenticated user inject this:

    @Inject
    @AuthenticatedUser
    User authenticatedUser;
 */
@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private User authenticatedUser;

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = findUser(username);
    }

    private User findUser(String username) {
        // Hit the the database or a service to find a user by its username and return it
        // Return the User instance
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        User u = (User) query.getSingleResult();
        System.out.println(u.toString());
        return u;
    }
}