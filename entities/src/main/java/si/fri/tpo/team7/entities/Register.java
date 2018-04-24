package si.fri.tpo.team7.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@MappedSuperclass
public class Register {

    @Id
    @Column(name = "id", length = 7)
    protected int id;
}
