package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="token")
    private EnrollmentToken token;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private Set<EnrollmentCourse> courses;
}
