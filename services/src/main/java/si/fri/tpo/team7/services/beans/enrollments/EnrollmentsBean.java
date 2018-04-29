package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.enrollments.Enrollment;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentsBean  extends EntityBean<Enrollment> {
    public EnrollmentsBean() {
        super(Enrollment.class);
    }
}
