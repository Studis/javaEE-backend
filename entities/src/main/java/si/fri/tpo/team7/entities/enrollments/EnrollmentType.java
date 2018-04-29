package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EnrollmentType extends Register {

    @Column(name="name")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
