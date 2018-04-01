package si.fri.tpo.team7.entities.users;

import javax.persistence.*;

@Entity
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    protected int id;

    @Column(length = 30)
    protected String name;

    @Column(length = 30)
    protected String surname;

    @Column(length = 30)
    protected String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
