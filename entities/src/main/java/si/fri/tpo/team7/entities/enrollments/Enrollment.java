package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="token")
    private EnrollmentToken token;

    @ManyToOne
    @JoinColumn(name="program")
    private Program program;

    @ManyToOne
    @JoinColumn(name="type")
    private EnrollmentType type;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private Set<EnrollmentCourse> courses;

    public EnrollmentToken getToken() {
        return token;
    }

    public void setToken(EnrollmentToken token) {
        this.token = token;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }
}
