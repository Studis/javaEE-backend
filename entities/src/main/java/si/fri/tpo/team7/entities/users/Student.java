package si.fri.tpo.team7.entities.users;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.removeStudent", query = "DELETE FROM Student s WHERE s.id = :id")
        })
public class Student extends User {

    @Column(name = "study_course", length = 7)
    protected List<String> studyCourse;

    @Column(name = "e_mail", length = 60)
    protected String eMail;

    //AUTOGENERATE
    @Column(name = "enrollment_number", length = 8, unique = true)
    protected String enrollmentNumber;

    @Column(length = 30)
    protected String username;

    public List<String> getStudyCourse() {
        return studyCourse;
    }

    public void setStudyCourse(List<String> studyCourse) {
        this.studyCourse = studyCourse;
    }

    public void addStudyCourse(String studyCourse) {
        this.studyCourse.add(studyCourse);
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (getId() != student.getId()) return false;
        if (getName() != null ? !getName().equals(student.getName()) : student.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(student.getSurname()) : student.getSurname() != null)
            return false;
        if (getStudyCourse() != null ? !getStudyCourse().equals(student.getStudyCourse()) : student.getStudyCourse() != null)
            return false;
        if (geteMail() != null ? !geteMail().equals(student.geteMail()) : student.geteMail() != null) return false;
        if (getEnrollmentNumber() != null ? !getEnrollmentNumber().equals(student.getEnrollmentNumber()) : student.getEnrollmentNumber() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(student.getUsername()) : student.getUsername() != null)
            return false;
        return getPassword() != null ? getPassword().equals(student.getPassword()) : student.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = 0;//getId();
        //result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        //result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getStudyCourse() != null ? getStudyCourse().hashCode() : 0);
        result = 31 * result + (geteMail() != null ? geteMail().hashCode() : 0);
        result = 31 * result + (getEnrollmentNumber() != null ? getEnrollmentNumber().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        //result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }
}
