package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Module extends BaseEntity {

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<ModuleCourse> courses;

    @ManyToOne
    @JoinColumn(name="studyYear", referencedColumnName = "id", nullable=false)
    private StudyYear studyYear;

    @Column(name="name")
    private String name;

    public List<ModuleCourse> getCourses() { return courses; }

    public void setCourses(List<ModuleCourse> courses){this.courses = courses;}

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
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
