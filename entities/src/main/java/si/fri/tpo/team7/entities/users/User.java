package si.fri.tpo.team7.entities.users;


import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="user_type")
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

    @Column(name = "e_mail", length = 60)
    protected String eMail;

    @Column(length = 30)
    protected String username;

    @Column(unique = true, name = "password_reset_token")
    protected String passwordResetToken;

    public String getUniversityEmail(){ return username+"@fri.uni-lj.si"; }

    public abstract Role getRole();

    public void setPasswordResetToken() {
        this.passwordResetToken = UUID.randomUUID().toString();
    }

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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
