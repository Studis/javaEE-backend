package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "GeneralOptional")
public class GeneralOptionalCourse extends CourseExecution {

    @ManyToMany(mappedBy = "generalOptionalCourses")
    protected List<Curriculum> curriculums;

    @Override
    public Year getYear() {
        if (curriculums.size() > 0) return curriculums.get(0).getYear();
        else return null;
    }
}
