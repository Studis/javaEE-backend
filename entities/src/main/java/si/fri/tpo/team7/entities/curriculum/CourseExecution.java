package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "course_type")
@EqualsAndHashCode(exclude = "enrollmentCourses")
public class CourseExecution extends BaseEntity {

    @Column(name="winter")
    private boolean winter;

    @ManyToOne
    @JoinColumn(name="course", referencedColumnName = "id", nullable=false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="lecturer1", nullable=false)
    private Lecturer lecturer1;

    @ManyToOne
    @JoinColumn(name="lecturer2", nullable=true)
    private Lecturer lecturer2;

    @ManyToOne
    @JoinColumn(name="lecturer3", nullable=true)
    private Lecturer lecturer3;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseExecution")
    private List<EnrollmentCourse> enrollmentCourses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseExecution")
    private List<Exam> exams;

    public Set<Lecturer> getLecturers(){
        Set<Lecturer> set = new HashSet<>();
        set.add(lecturer1);
        if(lecturer2 != null) set.add(lecturer2);
        if(lecturer3 != null) set.add(lecturer3);
        return set;
    }

    public boolean isWinter() {
        return winter;
    }

    public int getEnrollmentsCount(){
        return enrollmentCourses.size();
    }

    public Year getYear() {
        return null;
    }
}
