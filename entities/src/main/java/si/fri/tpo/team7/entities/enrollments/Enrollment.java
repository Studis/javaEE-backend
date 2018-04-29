package si.fri.tpo.team7.entities.enrollments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.StudyYear;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="token")
    private EnrollmentToken token;

    @ManyToOne
    @JoinColumn(name="studyYear1")
    private StudyYear studyYear1;

    @ManyToOne
    @JoinColumn(name="studyYear2")
    private StudyYear studyYear2;

    @ManyToOne
    @JoinColumn(name="type")
    private EnrollmentType type;

    @ManyToOne
    @JoinColumn(name="study_type")
    private StudyType studyType;

    @ManyToOne
    @JoinColumn(name="study_form")
    private StudyForm studyForm;

    @ManyToOne
    @JoinColumn(name="curriculum", referencedColumnName = "id", nullable=false)
    private Curriculum curriculum;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="enrollment")
    private Set<EnrollmentCourse> courses;

    public EnrollmentToken getToken() {
        return token;
    }

    public void setToken(EnrollmentToken token) {
        this.token = token;
    }

    public StudyYear getStudyYear1() {
        return studyYear1;
    }

    public void setStudyYear1(StudyYear studyYear1) {
        this.studyYear1 = studyYear1;
    }

    public StudyYear getStudyYear2() {
        return studyYear2;
    }

    public void setStudyYear2(StudyYear studyYear2) {
        this.studyYear2 = studyYear2;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Set<EnrollmentCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<EnrollmentCourse> courses) {
        this.courses = courses;
    }
}
