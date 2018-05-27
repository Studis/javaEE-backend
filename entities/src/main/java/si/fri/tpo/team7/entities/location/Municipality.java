package si.fri.tpo.team7.entities.location;

import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Data
@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Municipality.getAll", query = "SELECT m FROM Municipality m"),
                @NamedQuery(name = "Municipality.getByName", query = "SELECT m FROM Municipality m WHERE m.name = :name")
        })
public class Municipality extends Register {

    @Column(name="name")
    protected String name;
}
