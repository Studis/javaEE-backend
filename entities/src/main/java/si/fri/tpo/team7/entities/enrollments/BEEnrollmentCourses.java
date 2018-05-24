package si.fri.tpo.team7.entities.enrollments;

import lombok.Data;
import si.fri.tpo.team7.entities.curriculum.GeneralOptionalCourse;
import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.entities.curriculum.ProfessionalOptionalCourse;

import java.util.List;

@Data
public class BEEnrollmentCourses {

    List<ModuleCourse> moduleCourseList;

    List<ObligatoryCourse> obligatoryCourseList;

    List<ProfessionalOptionalCourse> professionalOptionalCourseList;

    List<GeneralOptionalCourse> generalOptionalCourseList;

}
