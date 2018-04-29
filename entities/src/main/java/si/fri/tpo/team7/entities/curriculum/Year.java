package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class Year extends Register {
    //@OneToMany(cascade=CascadeType.ALL, mappedBy="year", fetch = FetchType.EAGER)
    //@OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name="year_id")
    //@MapKey
    //private Map<Integer, StudyYear> semesters;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="year")
    private Set<Curriculum> curriculums;

    @JsonGetter
    @Override
    public String toString(){
        return id+"/"+(id+1);
    }
}
