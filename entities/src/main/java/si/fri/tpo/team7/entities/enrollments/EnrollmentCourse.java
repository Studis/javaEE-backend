package si.fri.tpo.team7.entities.enrollments;

import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class EnrollmentCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="enrollment", referencedColumnName = "id", nullable=false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="courseExecution", referencedColumnName = "id", nullable=false)
    private CourseExecution courseExecution;
}
