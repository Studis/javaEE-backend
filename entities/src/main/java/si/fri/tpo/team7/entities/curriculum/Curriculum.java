package si.fri.tpo.team7.entities.curriculum;


import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.Enrollment;

import javax.persistence.*;
import java.util.List;

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

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
