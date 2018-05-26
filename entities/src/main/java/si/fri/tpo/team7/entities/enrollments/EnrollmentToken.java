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
@EqualsAndHashCode(exclude = "enrollments")
public class EnrollmentToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="student", referencedColumnName = "id", nullable=false)
    private Student student;

    @OneToOne
    @JsonIgnore
    private Enrollment enrollment;

    private Status status;

    private boolean freeChoice;

    @OneToOne
    private StudyForm studyForm;

    @OneToOne
    private StudyType studyType;

    @OneToOne
    private EnrollmentType enrollmentType;

    @OneToOne
    private StudyYear studyYear;

    @OneToOne
    private Program program;

    @Override
    public String toString() {
        return "EnrollmentToken{" +
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
