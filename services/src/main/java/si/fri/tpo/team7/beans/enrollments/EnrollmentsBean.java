package si.fri.tpo.team7.beans.enrollments;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.enrollments.Enrollment;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentsBean  extends Bean<Enrollment>{
    public EnrollmentsBean() {
        super(Enrollment.class);
    }
}
