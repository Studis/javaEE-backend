package si.fri.tpo.team7.entities.users;

import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.users.Municipality;

import javax.persistence.*;

@Data
@Entity
public class Residence extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="municipality", nullable=false)
    private Municipality municipality;

    private String country;

    private String placeOfResidence;

    private String postalNumber;
}
