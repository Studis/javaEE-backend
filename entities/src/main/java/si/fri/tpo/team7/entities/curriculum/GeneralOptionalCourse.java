package si.fri.tpo.team7.entities.curriculum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue(value = "GeneralOptional")
public class GeneralOptionalCourse extends CourseExecution {

    @OneToMany
    protected List<Curriculum> curriculums;
}