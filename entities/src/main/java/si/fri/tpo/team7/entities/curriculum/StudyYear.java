package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class StudyYear extends Register {

    @JsonGetter
    @Override
    public String toString(){
        return id+". letnik";
    }
}
