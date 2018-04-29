package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Clerk")
public class Clerk extends User {

    @Override
    public Role getRole() {
        return Role.CLERK;
    }
}
