package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@DiscriminatorValue(value = "ProfessionalOptional")
public class ProfessionalOptionalCourse extends CourseExecution {

    @ManyToMany(mappedBy = "professionalOptionalCourses")
    protected List<Curriculum> curriculums;

    @Override
    public Year getYear() {
        if (curriculums.size() > 0) return curriculums.get(0).getYear();
        else return null;
    }
}
