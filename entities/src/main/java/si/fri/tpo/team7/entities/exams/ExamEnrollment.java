package si.fri.tpo.team7.entities.exams;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Column(name="score")
    private int score;

    public EnrollmentCourse getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(EnrollmentCourse enrollment) {
        this.enrollment = enrollment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Exam getExam() {

        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
