package si.fri.tpo.team7.services.beans.users;

import si.fri.tpo.team7.entities.users.Lecturer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class LecturersBean {
    private Logger log = Logger.getLogger(LecturersBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Transactional
    public List<Lecturer> getLecturers() {

        return em.createNamedQuery("Lecturer.getAll", Lecturer.class).getResultList();
    }

    @Transactional
    public Lecturer getLecturer(int id){
        Lecturer lecturer = em.find(Lecturer.class, id);
        if(lecturer == null) {
            throw new NotFoundException("Lecturer " + id + " not found.");
        }
        return lecturer;
    }

    @Transactional
    public Lecturer addLecturer(Lecturer s) {
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
    public void removeLecturer(int id) {
        Lecturer lecturer = em.find(Lecturer.class, id);
        if(lecturer == null) {
            throw new NotFoundException("Lecturer " + id + " not found.");
        }
        em.remove(lecturer);
    }

    @Transactional
    public Lecturer updateLecturer(int id, Lecturer s) {
        Lecturer lecturer = em.find(Lecturer.class, id);
        if(lecturer == null) {
            throw new NotFoundException("Lecturer " + id + " not found.");
        }
        s.setId(id);
        em.merge(s);
        return s;
    }
}
