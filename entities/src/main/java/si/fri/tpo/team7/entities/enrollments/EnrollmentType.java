package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class EnrollmentType extends Register {

    @Column(name="name")
    protected String name;

    @JsonGetter
    @Override
    public String toString(){
        return name;
    }
}
