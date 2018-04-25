package si.fri.tpo.team7.entities.exams;

import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Exam extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;

    @Column(name="written")
    private boolean written;
}
