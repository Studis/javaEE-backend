package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "ProfessionalOptional")
public class ProfessionalOptionalCourse extends CourseExecution {

    @JsonBackReference
    @ManyToMany(mappedBy = "professionalOptionalCourses", fetch = FetchType.EAGER)
    protected List<Curriculum> curriculums;

    @Override
    public Year getYear() {
        if (curriculums.size() > 0) return curriculums.get(0).getYear();
        else return null;
    }
}
