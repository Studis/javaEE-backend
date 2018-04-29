package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Exam extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;

    @Column(name="written")
    private boolean written;


    // To import exams from past
    @JsonIgnore
    private boolean pastImport;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledAt;

}
