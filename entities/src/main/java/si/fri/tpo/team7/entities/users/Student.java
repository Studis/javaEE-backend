package si.fri.tpo.team7.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.oxm.json.JsonObjectBuilderResult;
import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import si.fri.tpo.team7.entities.curriculum.Module;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Student")
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.removeStudent", query = "DELETE FROM Student s WHERE s.id = :id")
        })
public class Student extends User {

    @Column(name = "study_course", length = 7)
    protected List<String> studyCourse;

    //AUTOGENERATE
    @Column(name = "enrollment_number", length = 8, unique = true)
    protected String enrollmentNumber;

    @Column(length = 13, unique = true)
    protected String emso;

    protected String municipality;

    protected String country;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<EnrollmentToken> enrollmentTokens;

    public List<EnrollmentToken> getEnrollmentTokens() {
        return enrollmentTokens;
    }

    public void setEnrollmentTokens(List<EnrollmentToken> enrollmentTokens) {
        this.enrollmentTokens = enrollmentTokens;
    }

    public void setStudyCourse(List<String> studyCourse) {
        this.studyCourse = studyCourse;
    }

    public void addStudyCourse(String studyCourse) {
        this.studyCourse.add(studyCourse);
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (!equals(o)) return false;
        if (geteMail() != null ? !geteMail().equals(student.geteMail()) : student.geteMail() != null) return false;
        if (getEnrollmentNumber() != null ? !getEnrollmentNumber().equals(student.getEnrollmentNumber()) : student.getEnrollmentNumber() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(student.getUsername()) : student.getUsername() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (geteMail() != null ? geteMail().hashCode() : 0);
        result = 31 * result + (getEnrollmentNumber() != null ? getEnrollmentNumber().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student " + name + " "+surname;
    }

    public String toJson(){
        String builder = "{" +
                "\"name\": \"" + name + "\"," +
                "\"surname\": \"" + surname + "\"," +
                "\"email\": \"" + eMail + "\"," +
                "\"username\": \"" + username + "\""
                +"}";
        return builder;
    }
}
