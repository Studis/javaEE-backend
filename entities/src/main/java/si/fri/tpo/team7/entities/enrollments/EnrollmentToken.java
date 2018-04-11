package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.users.Student;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EnrollmentToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="student", referencedColumnName = "id", nullable=false)
    private Student student;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="token")
    private Set<Enrollment> enrollments;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
