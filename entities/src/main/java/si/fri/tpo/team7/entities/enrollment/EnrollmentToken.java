package si.fri.tpo.team7.entities.enrollment;

import si.fri.tpo.team7.entities.curriculum.Module;
import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EnrollmentToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="student")
    private Student student;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="token")
    private Set<Enrollment> enrollments;
}
