package si.fri.tpo.team7.entities.enrollments;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EnrollmentType extends BaseEntity {
    @Column(name="name")
    protected String name;

    @Column(name="code")
    protected int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
