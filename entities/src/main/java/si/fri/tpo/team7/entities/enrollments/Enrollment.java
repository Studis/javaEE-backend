package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.curriculum.Semester;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="token")
    private EnrollmentToken token;

    @ManyToOne
    @JoinColumn(name="semester1")
    private Semester semester1;

    @ManyToOne
    @JoinColumn(name="semester2")
    private Semester semester2;

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

    public Semester getSemester1() {
        return semester1;
    }

    public void setSemester1(Semester semester) {
        this.semester1 = semester;
    }

    public Semester getSemester2() {
        return semester2;
    }

    public void setSemester2(Semester semester) {
        this.semester2 = semester;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }
}
