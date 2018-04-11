package si.fri.tpo.team7.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import java.time.Instant;

@Entity
public class FailedLogin extends BaseEntity {

    @Column(name="ip", unique = true)
    private String ip;

    @Column(name="timestamp")
    private Instant timestamp;

    @Column(name="trials")
    private int trials;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getTrials() {
        return trials;
    }

    public void setTrials(int trials) {
        this.trials = trials;
    }
}
