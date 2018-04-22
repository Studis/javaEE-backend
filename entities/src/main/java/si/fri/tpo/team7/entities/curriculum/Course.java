package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.Register;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class Course extends Register {
    @Column(name="name")
    private String name;

    @Column(name="ects")
    private int ects;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseExecution> courseExecutions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public List<CourseExecution> getCourseExecutions() { return courseExecutions; }


}
