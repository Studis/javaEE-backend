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
import si.fri.tpo.team7.services.beans.validators.DateValidator;
import si.fri.tpo.team7.services.interceptors.ExamEnrollmentInterceptor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.NotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ExamEnrollmentsSeeder extends Seeder {
    ExamsBean examsBean;
    ExamEnrollmentBean examEnrollmentBean;
    EnrollmentCoursesBean enrollmentCoursesBean;
    StudentsBean studentsBean;

    private Logger log = Logger.getLogger(ExamEnrollmentsSeeder.class.getName());
    public ExamEnrollmentsSeeder(ExamsBean examsBean, ExamEnrollmentBean examEnrollmentBean, EnrollmentCoursesBean enrollmentCoursesBean, StudentsBean studentsBean) {
        super("exam enrollments");
        this.examsBean = examsBean;
        this.examEnrollmentBean = examEnrollmentBean;
        this.enrollmentCoursesBean = enrollmentCoursesBean;
        this.studentsBean = studentsBean;
    }

    @Override
    protected void SeedContent() {
        List<Student> students = studentsBean.getStudents();

        for (Student student: students) {

            for (EnrollmentCourse enrollmentCourse: enrollmentCoursesBean.get()) {
                if (student.getId() == enrollmentCourse.getEnrollment().getToken().getStudent().getId()) { // If student has token then he can enroll
                    for (Exam exam : enrollmentCourse.getCourseExecution().getExams()) {
                        if (student.getId() != 1 || true) {
                            ExamEnrollment e = new ExamEnrollment();
                            e.setEnrollmentCourse(enrollmentCourse);
                            e.setExam(exam);
                            if (Instant.now()
                                    .isBefore(
                                            exam.getScheduledAt()
                                                    .toInstant()
                                                    .truncatedTo(ChronoUnit.DAYS)
                                                    .plus(23, ChronoUnit.HOURS)
                                                    .plus(55, ChronoUnit.MINUTES)
                                                    .minus(ExamEnrollmentInterceptor.MAX_DAYS_BEFORE_EXAM_APPLY, ChronoUnit.DAYS)
                                    )
                                    ) { // If exam is scheduled in the future
                                if (exam.getCourseExecution().getId() == enrollmentCourse.getCourseExecution().getId()) {
                                    e.setPastImport(true);
                                    examEnrollmentBean.add(e);
                                    //break;
                                }
                            } else if (exam.getScheduledAt().toInstant().isBefore(Instant.now())) { // Past enrollments
                                Random rn = new Random();
                                Integer max = 100;
                                Integer min = 0;
                                Integer score = rn.nextInt(max - min + 1) + min;
                                if (exam.getId() != 76) { // Aps kononenko 2018-05-21 { }
                                    e.setScore(score);
                                    e.setMark(getMarkFromScore(score));
                                }

                                e.setPastImport(true);
                                examEnrollmentBean.add(e);
                                //break;
                            }
                        }
                        /*
                         * Franc Župančič UNI
                         * 1. letnik
                         * Pavzer
                         * 1. letnik Ponavlja
                         * 2.letnik
                         * Ima žeton za 3. letnik
                         */
                        if (false && student.getId() == 1) {
//                            ExamEnrollment franc = new ExamEnrollment();
//                            franc.setEnrollmentCourse(enrollmentCourse);
//                            franc.setExam(exam);

//
//                            makeStudentWrite(franc,30,1);
//                            makeStudentWrite(franc,51,2);

                            log.info("Year" + Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4)));
//                            if (Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2015) { // Past enrollments
//
//                                makeStudentWrite(franc,30,1);
//                            }
//                            if (Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2016) { // Past enrollments
//
//                                makeStudentWrite(franc,40,2);
//                            }
//                            if (Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2017) { // Past enrollments
//
//                                makeStudentWrite(franc,45,2);
//                            }
//                            if (Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2018) { // Past enrollments
//
//                                makeStudentWrite(franc,70,2);
//                            }
//                                if (enrollmentCourse.getEnrollment().getCurriculum().getYear().getId() == 2015 && Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2015) {
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63277) { // Programiranje 1
//                                        makeStudentWrite(franc,55,1);
//                                    }
//                                    if (exam.getCourseExecution().getCourse().getId() == 63203) { // Diskretne strukture
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,40,2);
//                                        makeStudentWrite(franc,45,3);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63205) { // Fizika
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,40,2);
//                                        makeStudentWrite(franc,45,3);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63204) { // ODV
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,40,2);
//                                        makeStudentWrite(franc,45,3);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63202) { // OMA
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,40,2);
//                                        makeStudentWrite(franc,45,3);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63278) { // P2
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,40,2);
//                                        makeStudentWrite(franc,45,3);
//                                    }
//
//                                }
//
//
//                                if (enrollmentCourse.getEnrollment().getCurriculum().getYear().getId() == 2015 && Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2016) {
//
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63203) { // Diskretne strukture
//                                        makeStudentWrite(franc,49,1);
//                                        makeStudentWrite(franc,48,2);
//                                        makeStudentWrite(franc,90,3);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63205) { // Fizika
//                                        makeStudentWrite(franc,89,1);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63204) { // ODV
//                                        makeStudentWrite(franc,60,1);
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63202) { // OMA
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//
//                                    }
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63278) { // P2
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//                                    }
//
//
//
//
//                                }
//                                if (enrollmentCourse.getEnrollment().getCurriculum().getYear().getId() == 2015 && Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0,4))== 2018) {
//
//
//
//                                    if (exam.getCourseExecution().getCourse().getId() == 63212) { // ARS
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//                                    }
//                                    if (exam.getCourseExecution().getCourse().getId() == 63209) { // RK
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//                                    }
//                                    if (exam.getCourseExecution().getCourse().getId() == 63207) { // LA
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//                                    }
//                                    if (exam.getCourseExecution().getCourse().getId() == 63215) { // OIS
//                                        makeStudentWrite(franc,30,1);
//                                        makeStudentWrite(franc,60,2);
//                                    }
//
//
//
//
//
//                                }


                                //break;
//                            }

                        }
                    }


                }
            }
        }

    }
    public void makeStudentWrite(ExamEnrollment stud, Integer scoreE, Integer term) {
        if (stud.getExam().getExamTerm() == term) {
            Integer score = scoreE;
            stud.setScore(score);
            stud.setMark(getMarkFromScore(score));
            stud.setPastImport(true);
            examEnrollmentBean.add(stud);
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

