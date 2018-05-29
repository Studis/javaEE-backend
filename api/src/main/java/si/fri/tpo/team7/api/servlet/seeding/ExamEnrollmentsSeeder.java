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
import si.fri.tpo.team7.services.interceptors.ExamEnrollmentInterceptor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

        calendar.set(2017,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamEnrollments(calendar,exams,courseExecutions, false);
        calendar.set(2017,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamEnrollments(calendar,exams,courseExecutions, false);
        calendar.set(2017,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamEnrollments(calendar,exams,courseExecutions, false);

    }

    public void seedExamEnrollments (Calendar calendar, List<Exam> exams, List<ExamEnrollment> examEnrollments, boolean pastImport) {

        List<Student> students = studentsBean.getStudents();
        List<ExamEnrollment> examEnrollmentsAll = examEnrollmentBean.get();
        for (Student student: students) {
            for (EnrollmentCourse enrollmentCourse: enrollmentCoursesBean.get()) {
                if (student.getId() == enrollmentCourse.getEnrollment().getToken().getStudent().getId()) { // If student has token then he can enroll
                    for (Exam exam : enrollmentCourse.getCourseExecution().getExams()) {
                        if (       exam.getCourseExecution().getId() == enrollmentCourse.getCourseExecution().getId()
                                && enrollmentCourse.getCourseExecution().getId() == exam.getCourseExecution().getId()) { // Apply for the right exams and only once
                            if (Instant.now().isBefore(exam.getScheduledAt().toInstant().truncatedTo(ChronoUnit.DAYS).plus(23, ChronoUnit.HOURS).plus(55, ChronoUnit.MINUTES).minus(ExamEnrollmentInterceptor.MAX_DAYS_BEFORE_EXAM_APPLY, ChronoUnit.DAYS))) { // If exam is scheduled in the future
                                Random rn = new Random();
                                Integer max = 100;
                                Integer min = 0;
                                if (exam.getCourseExecution().getId() == enrollmentCourse.getCourseExecution().getId()) {
                                    ExamEnrollment e = new ExamEnrollment();
                                    e.setEnrollmentCourse(enrollmentCourse);
                                    e.setExam(exam);
                                    examEnrollmentBean.add(e);
                                    break;
                                }
                            } else if (exam.getScheduledAt().toInstant().isBefore(Instant.now())) { // Past enrollments
                                Random rn = new Random();
                                Integer max = 100;
                                Integer min = 0;
                                ExamEnrollment e = new ExamEnrollment();
                                e.setEnrollmentCourse(enrollmentCourse);
                                e.setExam(exam);
                                if (exam.getCourseExecution().getId() == enrollmentCourse.getCourseExecution().getId()) {
                                    Integer score = rn.nextInt(max - min + 1) + min;
                                    if (exam.getId() != 76) { // Aps kononenko 2018-05-21 { }
                                        e.setScore(score);
                                        e.setMark(getMarkFromScore(score));
                                    }

                                    e.setPastImport(true);
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

    public static Integer getMarkFromScore(Integer score) {
        if (score > 89) { // 100 - 90
            return 10;
        } else if (score > 79) { // 89 - 70
            return 9;
        } else if (score > 69) { // 60 -
            return 8;
        } else if (score > 59) {
            return 7;
        } else if (score > 49) {
            return 6;
        }
        return 5;
    }
}

