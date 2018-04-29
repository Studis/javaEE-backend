package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue(value = "Module")
public class ModuleCourse extends CourseExecution {

    @ManyToOne
    @JoinColumn(name="module", referencedColumnName = "id", nullable=false)
    private Module module;
}
