package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.Register;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Municipality extends Register {

    @Column(name="name")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
