package si.fri.tpo.team7.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import java.time.Instant;

@Data
@Entity
public class FailedLogin extends BaseEntity {

    @Column(name="ip", unique = true)
    private String ip;

    @Column(name="timestamp")
    private Instant timestamp;

    @Column(name="trials")
    private int trials;
}
