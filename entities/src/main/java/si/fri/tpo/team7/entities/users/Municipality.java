package si.fri.tpo.team7.entities.users;

import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Municipality extends Register {

    @Column(name="name")
    protected String name;
}
