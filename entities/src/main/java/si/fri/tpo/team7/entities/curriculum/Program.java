package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Program extends BaseEntity {

    @OneToMany(cascade=CascadeType.ALL, mappedBy="program")
    private Set<Semester> semesters;

    @Column(name="ects")
    private int ects;

    @Column(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }
}
