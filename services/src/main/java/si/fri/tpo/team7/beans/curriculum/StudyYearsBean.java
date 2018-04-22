package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.curriculum.Year;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudyYearsBean extends RegisterBean<StudyYear> {

    public StudyYearsBean() {
        super(StudyYear.class);
    }
}
