package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "enrollments")
public class EnrollmentToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="student", referencedColumnName = "id", nullable=false)
    private Student student;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="token")
    private Set<Enrollment> enrollments;
}
