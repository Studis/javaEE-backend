package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Module extends BaseEntity {

    @OneToMany(cascade=CascadeType.ALL, mappedBy="module")
    private Set<Course> courses;

    @ManyToOne
    @JoinColumn(name="semester")
    private Semester semester;

    @Column(name="obligatory")
    private boolean obligatory;

    @Column(name="name")
    private String name;

    public Set<Course> getCourses() { return courses; }
}
