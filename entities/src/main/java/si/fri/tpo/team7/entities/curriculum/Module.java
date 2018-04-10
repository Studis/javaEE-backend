package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Module extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name="semester", referencedColumnName = "id", nullable=false)
    private Semester semester;

    @Column(name="obligatory")
    private boolean obligatory;

    @Column(name="name")
    private String name;

    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses){this.courses = courses;}

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

}
