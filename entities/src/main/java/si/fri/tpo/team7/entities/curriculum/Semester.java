package si.fri.tpo.team7.entities.curriculum;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @Column(name="number")
    private int number;

    @ManyToOne
    @JoinColumn(name="year")
    private Year year;

    @ManyToOne
    @JoinColumn(name="program")
    private Program program;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="semester")
    private Set<Module> modules;
}
