package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.Register;

import javax.persistence.*;
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
}
