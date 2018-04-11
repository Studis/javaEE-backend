package si.fri.tpo.team7.beans.enrollments;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentTypesBean extends Bean<EnrollmentType> {
    public EnrollmentTypesBean() {
        super(EnrollmentType.class);
    }
}
