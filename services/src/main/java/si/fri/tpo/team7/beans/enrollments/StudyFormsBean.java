package si.fri.tpo.team7.beans.enrollments;

import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.enrollments.StudyForm;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudyFormsBean extends RegisterBean<StudyForm> {
    public StudyFormsBean() {
        super(StudyForm.class);
    }
}
