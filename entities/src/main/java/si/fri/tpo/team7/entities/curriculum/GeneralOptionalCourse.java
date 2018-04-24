package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "GeneralOptional")
public class GeneralOptionalCourse extends CourseExecution {

    @OneToMany
    protected List<Curriculum> curriculums;
}
