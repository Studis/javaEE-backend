package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class EnrollmentCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="enrollment", referencedColumnName = "id", nullable=false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="courseExecution", referencedColumnName = "id", nullable=false)
    private CourseExecution courseExecution;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enrollment")
    private List<ExamEnrollment> examEnrollments;

    @Override
    public String toString() {
        return "EnrollmentCourse{" +
                "courseExecution=" + courseExecution +
                '}';
    }
}
