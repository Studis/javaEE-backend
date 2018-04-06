package si.fri.tpo.team7.entities.enrollment;

import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;

@Entity
public class EnrollmentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="enrollment")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;
}
