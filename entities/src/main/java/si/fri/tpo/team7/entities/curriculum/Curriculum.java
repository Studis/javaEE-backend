package si.fri.tpo.team7.entities.curriculum;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.Register;
import si.fri.tpo.team7.entities.enrollments.Enrollment;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Curriculum extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="studyYear", referencedColumnName = "id", nullable=false)
    private StudyYear studyYear;

    @ManyToOne
    @JoinColumn(name="program", referencedColumnName = "id", nullable=false)
    private Program program;

    @ManyToOne
    @JoinColumn(name="year", referencedColumnName = "id", nullable=false)
    private Year year;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curriculum")
    private List<Enrollment> enrollments;
}
