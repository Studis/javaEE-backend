package si.fri.tpo.team7.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.location.Residence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue(value = "Student")
@EqualsAndHashCode(exclude = "enrollmentTokens")
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.removeStudent", query = "DELETE FROM Student s WHERE s.id = :id")
        })
public class Student extends User {

    @Column(length = 13, unique = true)
    protected String emso;

    protected LocalDate dateOfBirth;
    protected String placeOfBirth;
    protected String gender;
    protected String nationality;

    protected String taxNumber;
    protected String phoneNumber;

    @OneToOne
    protected Residence permanent;
    @OneToOne
    protected Residence temporary;
    protected boolean sendToTemporary = false;

    //AUTOGENERATE
    @Column(name = "enrollment_number", length = 8, unique = true)
    protected String enrollmentNumber;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<EnrollmentToken> enrollmentTokens;

    private boolean freshman = true;

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }


    @JsonIgnore
    public List<Enrollment> getEnrollments(){
        List<EnrollmentToken> enrollmentTokens = getEnrollmentTokens();
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        for (EnrollmentToken token:enrollmentTokens) {
            enrollments.add(token.getEnrollment());
        }
        return enrollments;
    }



    public String toJson() {
        return "{" +
                "\"name\": \"" + name + "\"," +
                "\"surname\": \"" + surname + "\"," +
                "\"email\": \"" + eMail + "\"," +
                "\"username\": \"" + username + "\""
                + "}";
    }
}
