package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "course_type")
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
    private Set<EnrollmentCourse> enrollmentCourses;

    public Set<Lecturer> getLecturers(){
        Set<Lecturer> set = new HashSet<>();
        set.add(lecturer1);
        if(lecturer2 != null) set.add(lecturer2);
        if(lecturer3 != null) set.add(lecturer3);
        return set;
    }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Lecturer getLecturer1() {
        return lecturer1;
    }

    public void setLecturer1(Lecturer lecturer1) {
        this.lecturer1 = lecturer1;
    }

    public Lecturer getLecturer2() {
        return lecturer2;
    }

    public void setLecturer2(Lecturer lecturer2) {
        this.lecturer2 = lecturer2;
    }

    public Lecturer getLecturer3() {
        return lecturer3;
    }

    public void setLecturer3(Lecturer lecturer3) {
        this.lecturer3 = lecturer3;
    }

    public Set<EnrollmentCourse> getEnrollmentCourses() {
        return enrollmentCourses;
    }

    public void setEnrollmentCourses(Set<EnrollmentCourse> enrollmentCourses) {
        this.enrollmentCourses = enrollmentCourses;
    }

    public boolean isWinter() {
        return winter;
    }

    public void setWinter(boolean winter) {
        this.winter = winter;
    }
}
