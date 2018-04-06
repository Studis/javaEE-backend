package si.fri.tpo.team7.entities.enrollment;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="token")
    private EnrollmentToken token;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private Set<EnrollmentCourse> courses;
}
