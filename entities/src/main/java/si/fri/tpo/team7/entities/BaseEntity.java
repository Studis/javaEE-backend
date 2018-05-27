package si.fri.tpo.team7.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@Entity
@MappedSuperclass
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.removeStudent", query = "DELETE FROM Student s WHERE s.id = :id")
        })
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 7)
    protected int id;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

}
