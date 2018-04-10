package si.fri.tpo.team7.beans;

import si.fri.tpo.team7.beans.users.LecturersBean;
import si.fri.tpo.team7.entities.users.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.logging.Logger;

@ApplicationScoped
public class AuthBean {
    private Logger log = Logger.getLogger(LecturersBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    public boolean validateCredentials(String username, String password) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        User u = (User) query.getSingleResult();

        if(u.getPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }
}
