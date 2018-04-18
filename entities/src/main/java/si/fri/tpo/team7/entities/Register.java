package si.fri.tpo.team7.entities;

import javax.persistence.*;

@Entity
@MappedSuperclass
public class Register {
    @Id
    @Column(name = "id", length = 7)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
