package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.stream.Collectors;

@Data
@Entity
public class ExamEnrollment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="exam")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="enrollmentCourse", referencedColumnName = "id", nullable=false)
    private EnrollmentCourse enrollmentCourse;

    @Column(name="mark")
    private Integer mark;

    @Column(name="score")
    private Integer score;

    @JsonIgnore
    private Boolean paid;

    // To import exam enrollments from past
    @JsonIgnore
    private boolean pastImport;


    @Basic
    private String status;

    private Boolean deleteConfirmed;

    private int totalExamAttempts;

    private int returnedExamAttempts;

    private int attemptsInCurrentStudyYear;


    // UserId that has deleted exam enrollment

    @Basic
    private Integer deletedBy;

    public boolean getPassed(){
        return mark != null && mark > 5;
    }

    public int getTotalExamAttempts () {
        if (this.totalExamAttempts == 0) {
            return 1;
        }
        else return this.totalExamAttempts;
    }

    @JsonGetter
    public int getTotalAttempts() {
        return (int)enrollmentCourse
                .getExamEnrollments()
                .stream()
                .filter(p -> p.getExam().getScheduledAt().before(exam.getScheduledAt()))
                .count()+1;
    }

    @Transient
    public int attemptsInYear;

    public int getAttemptsInYear(Year year){
        return (int) enrollmentCourse
                .getExamEnrollments()
                .stream()
                .filter(p -> year.isInYear(p.getExam().getScheduledAt()))
                .filter(p -> p.getExam().getScheduledAt().before(exam.getScheduledAt()))
                .count()+1;
    }

    @JsonGetter
    public int RealTotal() {
        Integer sum = 0;
        for (Enrollment en : enrollmentCourse.getEnrollment().getToken().getStudent().getEnrollments()) {
            // en.getCourses().stream().filter(p -> p.getCourseExecution().getCourse().getId() == enrollmentCourse.getCourseExecution().getCourse().getId())

            for(EnrollmentCourse ec: en.getCourses()) {
                if (ec.getCourseExecution().getCourse().getId() == enrollmentCourse.getCourseExecution().getCourse().getId()) {
                    sum += (int)ec.getExamEnrollments().stream()
                            .filter(p -> p.getExam().getScheduledAt()
                                    .before(exam.getScheduledAt())).count()+1;
                }
            }
        }
        return sum;
    }
}
