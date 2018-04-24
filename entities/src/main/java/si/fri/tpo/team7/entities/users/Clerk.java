package si.fri.tpo.team7.entities.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.users.User;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "Clerk")
public class Clerk extends User {

    @Override
    public Role getRole() {
        return Role.CLERK;
    }
}
