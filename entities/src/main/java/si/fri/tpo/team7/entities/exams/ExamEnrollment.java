package si.fri.tpo.team7.entities.exams;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.persistence.*;

@Entity
public class ExamEnrollment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="exam")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="enrollments")
    private EnrollmentCourse enrollment;

    @Column(name="mark")
    private int mark;
}
