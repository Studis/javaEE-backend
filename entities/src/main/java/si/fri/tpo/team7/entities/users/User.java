package si.fri.tpo.team7.entities.users;


import lombok.Data;
import si.fri.tpo.team7.entities.enums.Role;

import javax.persistence.*;
import java.util.UUID;

@Data
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
}
