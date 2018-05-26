package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = "courses")
public class Module extends BaseEntity {

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<ModuleCourse> courses;

    @ManyToOne
    @JoinColumn(name="curriculum", referencedColumnName = "id", nullable=false)
    private Curriculum curriculum;

    @Column(name="name")
    private String name;

    @Override
    public String toString(){
        return name;
    }
}
