package si.fri.tpo.team7.beans.users;

import si.fri.tpo.team7.entities.users.Administrator;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class AdministratorBean {
    private Logger log = Logger.getLogger(LecturersBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Transactional
    public Administrator addAdmin(Administrator a) {
        if(a == null){
            return null;
        }
        em.persist(a);
        em.flush();
        return a;
    }
}
