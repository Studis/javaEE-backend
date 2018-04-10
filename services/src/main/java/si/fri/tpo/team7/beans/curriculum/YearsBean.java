package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class YearsBean {
    private Logger log = Logger.getLogger(Year.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Transactional
    public List<Year> getYears() {
        Query q = em.createNativeQuery("SELECT o FROM Year o");
        return (List<Year>)q.getResultList();
    }

    @Transactional
    public Year getYear(int id){
        Year year = em.find(Year.class, id);
        if(year == null) {
            throw new NotFoundException("Year " + id + " not found.");
        }
        em.refresh(year);
        return year;
    }

    @Transactional
    public Year addYear(Year s) {
        if(s == null){
            return null;
        }
        em.persist(s);
        em.flush();
        return s;
    }

    @Transactional
    public void removeYear(int id) {
        Year year = em.find(Year.class, id);
        if(year == null) {
            throw new NotFoundException("Year " + id + " not found.");
        }
        em.remove(year);
    }

    @Transactional
    public Year refresh(Year year){
        em.merge(year);
        em.refresh(year);
        return year;
    }

    @Transactional
    public Year updateYear(Year year) {
        em.merge(year);
        return year;
    }
}
