package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Exam extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="courseExecution")
    private CourseExecution courseExecution;

    @Column(name="written")
    private boolean written;


    // To import exams from past
    @JsonIgnore
    private boolean pastImport;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledAt;

    private String location;

    private String asking;

    @Transient
    private ExamEnrollment examEnrollment; // Transiend field for BEEnrollmentCourse entity

    private int examTerm;

    private Integer deletionRequestedByUserId;

}
