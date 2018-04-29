package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isWritten() {
        return written;
    }

    public void setWritten(boolean written) {
        this.written = written;
    }

    public boolean isPastImport() {
        return pastImport;
    }

    public void setPastImport(boolean pastImport) {
        this.pastImport = pastImport;
    }

    public Date getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Date scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
