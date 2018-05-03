package si.fri.tpo.team7.api.servlet.seeding;


import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.users.StudentsBean;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ExamEnrollmentsSeeder extends Seeder {
    ExamsBean examsBean;
    ExamEnrollmentBean examEnrollmentBean;
    EnrollmentCoursesBean enrollmentCoursesBean;
    StudentsBean studentsBean;

    public ExamEnrollmentsSeeder(ExamsBean examsBean, ExamEnrollmentBean examEnrollmentBean, EnrollmentCoursesBean enrollmentCoursesBean, StudentsBean studentsBean) {
        super("exam enrollments");
        this.examsBean = examsBean;
        this.examEnrollmentBean = examEnrollmentBean;
        this.enrollmentCoursesBean = enrollmentCoursesBean;
        this.studentsBean = studentsBean;
    }

    @Override
    protected void SeedContent() {
        List<Exam> exams = examsBean.get();
        List<ExamEnrollment> courseExecutions = examEnrollmentBean.get();

        Calendar calendar = Calendar.getInstance();

        calendar.set(2018,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamEnrollments(calendar,exams,courseExecutions, false);

    }

    public void seedExamEnrollments (Calendar calendar, List<Exam> exams, List<ExamEnrollment> examEnrollments, boolean pastImport) {

        List<Student> students = studentsBean.getStudents();
        List<ExamEnrollment> examEnrollmentsAll = examEnrollmentBean.get();
        for (Student student: students) {
            for (EnrollmentCourse enrollmentCourse: enrollmentCoursesBean.get()) {

                if (student.getId() == enrollmentCourse.getEnrollment().getToken().getStudent().getId()) { // If student has token then he can enroll
                    ExamEnrollment e = new ExamEnrollment();
                    for (Exam exam : examsBean.get()) {
                        if (exam.getScheduledAt().after(new Date())) { // If exam is scheduled in the future
                            Random rn = new Random();
                            Integer max = 10;
                            Integer min = 5;
                            if (exam.getCourseExecution().getId() == enrollmentCourse.getCourseExecution().getId()) {
                                e.setEnrollment(enrollmentCourse);
                                e.setExam(exam);
                                e.setMark(rn.nextInt(max - min + 1) + min);
                                max = 100;
                                min = 0;
                                e.setScore(rn.nextInt(max - min + 1) + min);
                                examEnrollmentBean.add(e);
                                break;
                            }
                        }

                    }
                }
            }


        }


    }
}

