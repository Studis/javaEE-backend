package si.fri.tpo.team7.entities.exams;



import lombok.Data;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;

import java.util.List;

@Data
public class BEEnrollmentCourse {
    public ExamEnrollment examEnrollment;
    public EnrollmentCourse enrollmentCourse;
    public List<Exam> examsAvailable;
}