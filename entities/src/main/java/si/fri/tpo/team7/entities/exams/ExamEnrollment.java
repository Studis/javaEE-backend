package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import javax.persistence.*;

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


    // UserId that has deleted exam enrollment

    @Basic
    private Integer deletedBy;
    
}
