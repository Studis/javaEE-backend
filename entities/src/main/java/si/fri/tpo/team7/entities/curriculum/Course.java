package si.fri.tpo.team7.entities.curriculum;

import lombok.Builder;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="ects")
    private int ects;

    @ManyToOne
    @JoinColumn(name="module", referencedColumnName = "id", nullable=false)
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Lecturer getLecturer1() {
        return lecturer1;
    }

    public void setLecturer1(Lecturer lecturer1) {
        this.lecturer1 = lecturer1;
    }

    public Lecturer getLecturer2() {
        return lecturer2;
    }

    public void setLecturer2(Lecturer lecturer2) {
        this.lecturer2 = lecturer2;
    }

    public Lecturer getLecturer3() {
        return lecturer3;
    }

    public void setLecturer3(Lecturer lecturer3) {
        this.lecturer3 = lecturer3;
    }

    @OneToMany(cascade=CascadeType.ALL, mappedBy="course")
    private Set<EnrollmentCourse> enrollments;
}
