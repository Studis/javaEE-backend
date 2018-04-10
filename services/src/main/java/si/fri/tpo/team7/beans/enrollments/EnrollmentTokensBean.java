package si.fri.tpo.team7.beans.enrollments;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentTokensBean extends Bean<EnrollmentToken> {
    public EnrollmentTokensBean() {
        super(EnrollmentToken.class);
    }
}
