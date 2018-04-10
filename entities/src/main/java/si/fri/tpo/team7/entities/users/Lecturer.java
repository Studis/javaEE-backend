package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.users.User;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Lecturer")
@NamedQueries(value =
        {
                @NamedQuery(name = "Lecturer.getAll", query = "SELECT s FROM Lecturer s")
        })
public class Lecturer extends User {

}
