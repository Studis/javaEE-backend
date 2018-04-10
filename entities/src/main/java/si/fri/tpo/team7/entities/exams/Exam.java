package si.fri.tpo.team7.entities.exams;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.*;

@Entity
public class Exam extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;

    @Column(name="written")
    private boolean written;


}
