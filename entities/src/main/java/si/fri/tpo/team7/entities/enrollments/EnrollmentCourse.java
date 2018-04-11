package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;

@Entity
public class EnrollmentCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="enrollment", referencedColumnName = "id", nullable=false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="course", referencedColumnName = "id", nullable=false)
    private Course course;

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
