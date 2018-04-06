package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.enrollment.EnrollmentCourse;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="ects")
    private int ects;

    @ManyToOne
    @JoinColumn(name="module", nullable=false)
    private Module module;

    @ManyToOne
    @JoinColumn(name="lecturer1", nullable=false)
    private Lecturer lecturer1;

    @ManyToOne
    @JoinColumn(name="lecturer2", nullable=true)
    private Lecturer lecturer2;

    @ManyToOne
    @JoinColumn(name="lecturer3", nullable=true)
    private Lecturer lecturer3;

    public Set<Lecturer> getLecturers(){
        Set<Lecturer> set = new HashSet<>();
        set.add(lecturer1);
        if(lecturer2 != null) set.add(lecturer2);
        if(lecturer3 != null) set.add(lecturer3);
        return set;
    }

    @OneToMany(cascade=CascadeType.ALL, mappedBy="course")
    private Set<EnrollmentCourse> enrollments;
}
