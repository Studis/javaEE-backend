package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Module extends BaseEntity {

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name="studyYear", referencedColumnName = "id", nullable=false)
    private StudyYear studyYear;

    @Column(name="obligatory")
    private boolean obligatory;

    @Column(name="name")
    private String name;

    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses){this.courses = courses;}

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
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
