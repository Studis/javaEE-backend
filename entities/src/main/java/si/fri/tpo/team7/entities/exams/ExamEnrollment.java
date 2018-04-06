package si.fri.tpo.team7.entities.exams;

import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.enrollment.EnrollmentCourse;

import javax.persistence.*;

@Entity
public class ExamEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="exam")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="enrollment")
    private EnrollmentCourse enrollment;

    @Column(name="mark")
    private int mark;
}
