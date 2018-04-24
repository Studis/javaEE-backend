package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@DiscriminatorValue(value = "ProfessionalOptional")
public class ProfessionalOptionalCourse extends CourseExecution {

    @OneToMany
    protected List<Curriculum> curriculums;

}
