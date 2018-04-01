package si.fri.tpo.team7.entities;

import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CourseExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @ManyToOne
    @JoinColumn(name="course", nullable=false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="lecturer1", nullable=false)
    private Lecturer lecturer1;

    @ManyToOne
    @JoinColumn(name="lecturer2", nullable=true)
    private Lecturer lecturer2;

    @ManyToOne
    @JoinColumn(name="lecturer3", nullable=true)
    private Lecturer lecturer3;

    @ManyToOne
    @JoinColumn(name="year")
    private StudyYear year;

    public StudyYear getYear() {
        return year;
    }

    public void setYear(StudyYear year) {
        this.year = year;
    }
    public Course getCourse() { return course; }

    public Set<Lecturer> getLecturers(){
        Set<Lecturer> set = new HashSet<>();
        set.add(lecturer1);
        set.add(lecturer2);
        set.add(lecturer3);
        return set;
    }
}
