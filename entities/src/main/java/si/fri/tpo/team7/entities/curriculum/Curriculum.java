package si.fri.tpo.team7.entities.curriculum;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.Enrollment;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude={
        "obligatoryCourses",
        "enrollments",
        "professionalOptionalCourses",
        "generalOptionalCourses",
        "modules"})
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curriculum")
    private List<ObligatoryCourse> obligatoryCourses;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "professionaloptionalcourse_curriculum",
            joinColumns = @JoinColumn(name = "curriculums_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "professionaloptionalcourse_id", referencedColumnName = "id")
    )
    private List<ProfessionalOptionalCourse> professionalOptionalCourses;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "generaloptionalcourse_curriculum",
            joinColumns = @JoinColumn(name = "curriculums_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "generaloptionalcourse_id", referencedColumnName = "id")
    )
    private List<GeneralOptionalCourse> generalOptionalCourses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curriculum")
    private List<Module> modules;
}
