package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.StudyYear;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "courses")
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="token")
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

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private Set<EnrollmentCourse> courses;

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
