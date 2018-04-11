package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.curriculum.Semester;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class SemestersBean extends Bean<Semester> {
    public SemestersBean() {
        super(Semester.class);
    }

    public Semester current(){
        Query q = em.createQuery("SELECT o FROM Semester o WHERE o.year = :year AND o.number = 1")
                .setParameter("year", Calendar.getInstance().get(Calendar.YEAR));
        return (Semester)q.getSingleResult();
    }

    public Semester next(){
        Query q = em.createQuery("SELECT o FROM Semester o WHERE o.year = :year AND o.number = 2")
                .setParameter("year", Calendar.getInstance().get(Calendar.YEAR));
        return (Semester)q.getSingleResult();
    }
}
