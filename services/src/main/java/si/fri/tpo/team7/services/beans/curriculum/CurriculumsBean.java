package si.fri.tpo.team7.services.beans.curriculum;

import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.Curriculum;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class CurriculumsBean extends EntityBean<Curriculum>{
    public CurriculumsBean() {
        super(Curriculum.class);
    }

    @Transactional
    public Curriculum get(Program program, Year year, StudyYear studyYear){
        Object obj = em.createQuery("SELECT p FROM Curriculum p WHERE p.program= :program AND p.year= :year AND p.studyYear= :studyYear")
                .setParameter("program", program)
                .setParameter("year", year)
                .setParameter("studyYear", studyYear)
                .getSingleResult();
        em.refresh(obj);
        return (Curriculum) obj;
    }
}
