package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Course extends Register {
    @Column(name="name")
    private String name;

    @Column(name="ects")
    private int ects;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseExecution> courseExecutions;
}
