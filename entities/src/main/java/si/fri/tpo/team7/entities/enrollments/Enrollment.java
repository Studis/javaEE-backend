package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.StudyYear;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"courses", "token"})
public class Enrollment extends BaseEntity {

    @OneToOne
    private EnrollmentToken token;

    @ManyToOne
    @JoinColumn(name="type")
    private EnrollmentType type;

    @ManyToOne
    @JoinColumn(name="study_type")
    private StudyType studyType;

    @ManyToOne
    @JoinColumn(name="study_form")
    private StudyForm studyForm;

    @ManyToOne
    @JoinColumn(name="curriculum", referencedColumnName = "id", nullable=false)
    private Curriculum curriculum;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private List<EnrollmentCourse> courses;

    @Override
    public String toString() {
        return "Enrollment{" +
                "type=" + type +
                ", studyType=" + studyType +
                ", studyForm=" + studyForm +
                ", curriculum=" + curriculum +
                ", courses=" + courses +
                '}';
    }
}
