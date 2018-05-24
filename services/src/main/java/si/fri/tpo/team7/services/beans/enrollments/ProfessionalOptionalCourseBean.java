package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.entities.curriculum.ProfessionalOptionalCourse;
import si.fri.tpo.team7.services.beans.EntityBean;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ProfessionalOptionalCourseBean extends EntityBean<ProfessionalOptionalCourse> {
    public ProfessionalOptionalCourseBean() {
        super(ProfessionalOptionalCourse.class);
    }
}