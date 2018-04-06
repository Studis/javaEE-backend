package si.fri.tpo.team7.entities.curriculum;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

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
}
