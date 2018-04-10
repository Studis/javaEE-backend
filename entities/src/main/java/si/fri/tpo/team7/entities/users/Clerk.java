package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.users.User;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue(value = "Clerk")
public class Clerk extends User {

    @Override
    public Role getRole() {
        return Role.CLERK;
    }
}
