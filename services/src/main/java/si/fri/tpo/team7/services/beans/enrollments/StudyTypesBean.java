package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.services.beans.RegisterBean;
import si.fri.tpo.team7.entities.enrollments.StudyType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudyTypesBean extends RegisterBean<StudyType> {
    public StudyTypesBean() {
        super(StudyType.class);
    }
}
