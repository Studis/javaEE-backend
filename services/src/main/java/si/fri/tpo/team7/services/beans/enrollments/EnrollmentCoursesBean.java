package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentCoursesBean extends EntityBean<EnrollmentCourse> {

    public EnrollmentCoursesBean() {
        super(EnrollmentCourse.class);
    }
}
