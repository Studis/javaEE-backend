package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Course extends Register {
    @Column(name="name")
    private String name;

    @Column(name="ects")
    private int ects;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseExecution> courseExecutions;

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public List<CourseExecution> getCourseExecutions() {
        return courseExecutions;
    }

    public void setCourseExecutions(List<CourseExecution> courseExecutions) {
        this.courseExecutions = courseExecutions;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
