package si.fri.tpo.team7.api.servlet.seeding;


import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.exams.Exam;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ExamEnrollmentsSeeder extends Seeder {
    ExamsBean examsBean;
    ExamEnrollmentBean examEnrollmentBean;
    EnrollmentCoursesBean enrollmentCoursesBean;

    public ExamEnrollmentsSeeder(ExamsBean examsBean, ExamEnrollmentBean examEnrollmentBean, EnrollmentCoursesBean enrollmentCoursesBean) {
        super("exam enrollments");
        this.examsBean = examsBean;
        this.examEnrollmentBean = examEnrollmentBean;
        this.enrollmentCoursesBean = enrollmentCoursesBean;
    }

    @Override
    protected void SeedContent() {
        List<Exam> exams = examsBean.get();
        List<ExamEnrollment> courseExecutions = examEnrollmentBean.get();

        Calendar calendar = Calendar.getInstance();

        // Seed exam enrollment for specific term

//        calendar.set(2018,Calendar.JANUARY,22); // Zimsko izpitno obdobje
//        seedExamSchedule(calendar,exams,courseExecutions, true);

        calendar.set(2018,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,exams,courseExecutions, false);

//        calendar.set(2018,Calendar.AUGUST,20); // Jesensko izpitno obdobje
//        seedExamSchedule(calendar,exams,courseExecutions,false);


    }

    public void seedExamSchedule (Calendar calendar, List<Exam> exams, List<ExamEnrollment> examEnrollments, boolean pastImport) {


        for (EnrollmentCourse enrollmentCourse: enrollmentCoursesBean.get()) {
            ExamEnrollment e = new ExamEnrollment();
            for (Exam exam: examsBean.get()) {
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
                    }
                }

            }
        }
    }
}

