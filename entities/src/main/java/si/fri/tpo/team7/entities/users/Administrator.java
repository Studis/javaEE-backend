package si.fri.tpo.team7.entities.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Administrator")
public class Administrator extends User {
}
