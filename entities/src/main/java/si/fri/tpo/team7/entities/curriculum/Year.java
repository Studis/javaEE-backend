package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Year extends Register {
    //@OneToMany(cascade=CascadeType.ALL, mappedBy="year", fetch = FetchType.EAGER)
    //@OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name="year_id")
    //@MapKey
    //private Map<Integer, StudyYear> semesters;

    public void setSemesters(Map<Integer, StudyYear> semesters) {
        this.semesters = semesters;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "year")
    @MapKey
    private Map<Integer, StudyYear> semesters;

    public Map<Integer, StudyYear> getSemesters() { return semesters; }

    @JsonGetter
    @Override
    public String toString(){
        return id+"/"+(id+1);
    }
}
