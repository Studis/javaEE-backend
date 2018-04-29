package si.fri.tpo.team7.entities.enrollments;

import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class StudyType extends Register {

    @Column(name="name")
    protected String name;
}