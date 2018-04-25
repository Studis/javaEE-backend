package si.fri.tpo.team7.entities.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
