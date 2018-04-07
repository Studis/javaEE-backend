package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;

@Entity
public class EnrollmentCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="enrollments")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;
}
