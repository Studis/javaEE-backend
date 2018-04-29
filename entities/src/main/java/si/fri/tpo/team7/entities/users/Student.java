package si.fri.tpo.team7.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Student")
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.removeStudent", query = "DELETE FROM Student s WHERE s.id = :id")
        })
public class Student extends User {

    @Column(length = 13, unique = true)
    protected String emso;
    protected Instant dateOfBirth;
    protected String placeOfBirth;
    protected String gender;



    protected String email;

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


    @Override
    public Role getRole() {
        return Role.STUDENT;
    }


    @JsonIgnore
    public List<Enrollment> getEnrollments(){
        List<EnrollmentToken> enrollmentTokens = getEnrollmentTokens();
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        for (EnrollmentToken token:enrollmentTokens) {
            enrollments.addAll(token.getEnrollments());
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

    public String getEmso() {
        return emso;
    }

    public void setEmso(String emso) {
        this.emso = emso;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Residence getPermanent() {
        return permanent;
    }

    public void setPermanent(Residence permanent) {
        this.permanent = permanent;
    }

    public Residence getTemporary() {
        return temporary;
    }

    public void setTemporary(Residence temporary) {
        this.temporary = temporary;
    }

    public boolean isSendToTemporary() {
        return sendToTemporary;
    }

    public void setSendToTemporary(boolean sendToTemporary) {
        this.sendToTemporary = sendToTemporary;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public List<EnrollmentToken> getEnrollmentTokens() {
        return enrollmentTokens;
    }

    public void setEnrollmentTokens(List<EnrollmentToken> enrollmentTokens) {
        this.enrollmentTokens = enrollmentTokens;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
