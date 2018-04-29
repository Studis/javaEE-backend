package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@DiscriminatorValue(value = "Lecturer")
@NamedQueries(value =
        {
                @NamedQuery(name = "Lecturer.getAll", query = "SELECT s FROM Lecturer s")
        })
public class Lecturer extends User {

        @Override
        public Role getRole() {
                return Role.LECTURER;
        }
}
