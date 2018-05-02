package si.fri.tpo.team7.services.beans.users;


import si.fri.tpo.team7.entities.users.Clerk;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ClerksBean {
    private Logger log = Logger.getLogger(ClerksBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Transactional
    public List<Clerk> getClerks() {

        return em.createNamedQuery("Clerk.getAll", Clerk.class).getResultList();
    }

    @Transactional
    public Clerk getClerk(int id){
        Clerk clerk = em.find(Clerk.class, id);
        if(clerk == null) {
            throw new NotFoundException("Lecturer " + id + " not found.");
        }
        return clerk;
    }

    @Transactional
    public Clerk addClerk(Clerk s) {
        if(s == null){
            return null;
        }
        em.persist(s);
        em.flush();
        s.setPassword(s.getUsername());
        s.setEMail(s.getUsername() + "@fri.uni-lj.si");
        return s;
    }

    @Transactional
    public void removeClerk(int id) {
        Clerk clerk = em.find(Clerk.class, id);
        if(clerk == null) {
            throw new NotFoundException("Clerk " + id + " not found.");
        }
        em.remove(clerk);
    }

    @Transactional
    public Clerk updateClerk(int id, Clerk s) {
        Clerk clerk = em.find(Clerk.class, id);
        if(clerk == null) {
            throw new NotFoundException("Clerk " + id + " not found.");
        }
        s.setId(id);
        return em.merge(s);
    }
}
