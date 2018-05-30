package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Program extends Register {

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="program")
    private Set<Curriculum> curriculums;
    
    @Column(name="ects")
    private int ects;

    @Column(name = "title")
    private String title;

    @JsonGetter
    @Override
    public String toString(){
        return title;
    }
}
