package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EnrollmentToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="student")
    private Student student;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="token")
    private Set<Enrollment> enrollments;
}