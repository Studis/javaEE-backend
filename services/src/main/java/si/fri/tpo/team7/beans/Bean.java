package si.fri.tpo.team7.beans;

import si.fri.tpo.team7.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;

public class Bean<T extends BaseEntity> {
    private Logger log;

    private final Class<T> _class;

    @PersistenceContext(unitName = "studis-jpa")
    protected EntityManager em;

    public Bean(Class<T> _class)
    {
        this._class = _class;
        log = Logger.getLogger(_class.getName());
    }

    @Transactional
    public List<T> get() {
        Query q = em.createQuery("SELECT o FROM "+_class.getSimpleName()+" o");
        return (List<T>)q.getResultList();
    }

    @Transactional
    public T get(int id){
        T obj = em.find(_class, id);
        if(_class == null) {
            throw new NotFoundException(_class.getName()+" " + id + " not found.");
        }
        em.refresh(obj);
        return obj;
    }

    @Transactional
    public T add(T obj) {
        if(obj == null){
            return null;
        }
        em.persist(obj);
        em.flush();
        return obj;
    }

    @Transactional
    public void remove(int id) {
        T obj = em.find(_class, id);
        if(obj == null) {
            throw new NotFoundException(_class.getName()+" " + id + " not found.");
        }
        em.remove(obj);
    }

    @Transactional
    public T update(int id, T s) {
        T obj = em.find(_class, id);
        if(obj == null) {
            throw new NotFoundException(_class.getName()+" " + id + " not found.");
        }
        s.setId(id);
        em.merge(s);
        return s;
    }

    @Transactional
    public void flush(){
        em.clear();
    }
}
