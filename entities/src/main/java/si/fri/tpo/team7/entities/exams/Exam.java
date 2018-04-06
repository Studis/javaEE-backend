package si.fri.tpo.team7.entities.exams;

import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.enrollment.EnrollmentToken;

import javax.persistence.*;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;

    @Column(name="written")
    private boolean written;


}
