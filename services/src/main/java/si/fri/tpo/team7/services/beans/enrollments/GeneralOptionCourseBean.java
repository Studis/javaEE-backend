package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.GeneralOptionalCourse;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.beans.RegisterBean;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;


@ApplicationScoped
public class GeneralOptionCourseBean extends EntityBean<GeneralOptionalCourse> {
    public GeneralOptionCourseBean() {
        super(GeneralOptionalCourse.class);
    }

}
