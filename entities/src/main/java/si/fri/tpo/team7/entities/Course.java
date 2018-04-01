package si.fri.tpo.team7.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    private String name;

    private int ects;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="course")
    private Set<CourseExecution> courseExecutions;

    public Set<CourseExecution> getExecutions() { return courseExecutions; }
}
