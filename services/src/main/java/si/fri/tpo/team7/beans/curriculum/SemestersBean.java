package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.StudyYear;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Calendar;

@ApplicationScoped
public class SemestersBean extends EntityBean<StudyYear> {
    public SemestersBean() {
        super(StudyYear.class);
    }

    public StudyYear current(){
        Query q = em.createQuery("SELECT o FROM StudyYear o WHERE o.year.year = :year AND o.number = 1")
                .setParameter("year", Calendar.getInstance().get(Calendar.YEAR));
        return (StudyYear)q.getSingleResult();
    }

    public StudyYear next(){
        Query q = em.createQuery("SELECT o FROM StudyYear o WHERE o.year.year = :year AND o.number = 2")
                .setParameter("year", Calendar.getInstance().get(Calendar.YEAR));
        return (StudyYear)q.getSingleResult();
    }
}
