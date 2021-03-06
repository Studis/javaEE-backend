package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Entity
public class EnrollmentCourse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="enrollment", referencedColumnName = "id", nullable=false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name="courseExecution", referencedColumnName = "id", nullable=false)
    private CourseExecution courseExecution;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enrollmentCourse")
    private List<ExamEnrollment> examEnrollments;

    @Override
    public String toString() {
        return "EnrollmentCourse{" +
                "courseExecution=" + courseExecution +
                '}';
    }

    @JsonIgnore
    public ExamEnrollment getLastFinishedExam(){
        if(examEnrollments.size() == 0) return null;
        return examEnrollments.stream()
                .filter(p -> p.getMark() != null)
                .min(Comparator.comparing(o -> o.getExam().getScheduledAt()))
                .orElse(null);
    }

    public Integer getMark(){
        ExamEnrollment e = getLastFinishedExam();
        if(e == null) return null;
        return e.getMark();
    }

    public boolean getPassed(){
        ExamEnrollment e = getLastFinishedExam();
        if(e == null) return false;
        return e.getPassed();
    }
}
