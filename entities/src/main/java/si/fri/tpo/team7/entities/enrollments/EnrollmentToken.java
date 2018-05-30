package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.enums.Status;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "enrollment")
public class EnrollmentToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="student", referencedColumnName = "id", nullable=false)
    private Student student;

    @OneToOne(mappedBy = "token")
    @JsonIgnore
    private Enrollment enrollment;

    private Status status;

    private boolean freeChoice;

    @ManyToOne
    @JoinColumn(name="study_form")
    private StudyForm studyForm;

    @ManyToOne
    @JoinColumn(name="study_type")
    private StudyType studyType;

    @ManyToOne
    @JoinColumn(name="enrollment_type")
    private EnrollmentType enrollmentType;

    @ManyToOne
    @JoinColumn(name="study_year")
    private StudyYear studyYear;

    @ManyToOne
    @JoinColumn(name="program")
    private Program program;

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", freeChoice=" + freeChoice +
                ", studyForm=" + studyForm +
                ", studyType=" + studyType +
                ", enrollmentType=" + enrollmentType +
                ", studyYear=" + studyYear +
                ", program=" + program +
                '}';
    }
}
