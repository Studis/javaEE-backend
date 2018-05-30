package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude={"curriculums"})
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

    public Date startDate(){
        Calendar c = Calendar.getInstance();
        c.set(id+1, 9, 30);
        return c.getTime();
    }

    public Date endDate(){
        Calendar c = Calendar.getInstance();
        c.set(id, 10, 1);
        return c.getTime();
    }

    public boolean isInYear(Date date){
        return date.after(startDate()) && date.before(endDate());
    }
}
