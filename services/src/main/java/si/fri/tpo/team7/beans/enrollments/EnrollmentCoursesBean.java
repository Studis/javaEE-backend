package si.fri.tpo.team7.beans.enrollments;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentCoursesBean extends Bean<EnrollmentCourse> {

    public EnrollmentCoursesBean() {
        super(EnrollmentCourse.class);
    }
}
