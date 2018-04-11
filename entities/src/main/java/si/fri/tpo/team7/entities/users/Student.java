package si.fri.tpo.team7.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.oxm.json.JsonObjectBuilderResult;
import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import si.fri.tpo.team7.entities.POJOs.Residence;
import si.fri.tpo.team7.entities.curriculum.Module;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    protected String region;
    protected String country;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return isSendToTemporary() == student.isSendToTemporary() &&
                Objects.equals(getEmso(), student.getEmso()) &&
                Objects.equals(getDateOfBirth(), student.getDateOfBirth()) &&
                Objects.equals(getPlaceOfBirth(), student.getPlaceOfBirth()) &&
                Objects.equals(getGender(), student.getGender()) &&
                Objects.equals(getRegion(), student.getRegion()) &&
                Objects.equals(getCountry(), student.getCountry()) &&
                Objects.equals(getTaxNumber(), student.getTaxNumber()) &&
                Objects.equals(getPhoneNumber(), student.getPhoneNumber()) &&
                Objects.equals(getPermanent(), student.getPermanent()) &&
                Objects.equals(getTemporary(), student.getTemporary()) &&
                Objects.equals(getEnrollmentNumber(), student.getEnrollmentNumber()) &&
                Objects.equals(getEnrollmentTokens(), student.getEnrollmentTokens());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getEmso(), getDateOfBirth(), getPlaceOfBirth(), getGender(), getRegion(), getCountry(), getTaxNumber(), getPhoneNumber(), getPermanent(), getTemporary(), isSendToTemporary(), getEnrollmentNumber(), getEnrollmentTokens());
    }

    @Override
    public String toString() {
        return "Student{" +
                "emso='" + emso + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", permanent=" + permanent +
                ", temporary=" + temporary +
                ", sendToTemporary=" + sendToTemporary +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", enrollmentTokens=" + enrollmentTokens +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", eMail='" + eMail + '\'' +
                ", username='" + username + '\'' +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                '}';
    }
}
