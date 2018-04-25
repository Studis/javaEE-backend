package si.fri.tpo.team7.entities.exams;

import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
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
