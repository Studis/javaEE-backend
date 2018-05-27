package si.fri.tpo.team7.services.dtos;

import lombok.Data;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;
import si.fri.tpo.team7.entities.enrollments.StudyForm;
import si.fri.tpo.team7.entities.enrollments.StudyType;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.entities.users.User;

import java.util.List;
import java.util.Set;

@Data
public class EnrollmentResponse {
    private Student student;

    private StudyType studyType;
    private StudyForm studyForm;
    private EnrollmentType enrollmentType;

    private Program program;
    private StudyYear studyYear;

    private int[] courses;

}
