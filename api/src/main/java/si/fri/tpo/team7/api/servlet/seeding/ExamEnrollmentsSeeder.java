package si.fri.tpo.team7.api.servlet.seeding;


import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
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
import java.util.*;
import java.util.logging.Logger;

public class ExamEnrollmentsSeeder extends Seeder {
    private final ExamSeeder examSeeder;
    ExamsBean examsBean;
    ExamEnrollmentBean examEnrollmentBean;
    EnrollmentCoursesBean enrollmentCoursesBean;
    StudentsBean studentsBean;
    EnrollmentsBean enrollmentsBean;


    private Logger log = Logger.getLogger(ExamEnrollmentsSeeder.class.getName());
    public ExamEnrollmentsSeeder(ExamsBean examsBean, ExamEnrollmentBean examEnrollmentBean,
                                 EnrollmentCoursesBean enrollmentCoursesBean, StudentsBean studentsBean,
                                 ExamSeeder examSeeder, EnrollmentsBean enrollmentsBean) {
        super("exam enrollments");
        this.examsBean = examsBean;
        this.examEnrollmentBean = examEnrollmentBean;
        this.enrollmentCoursesBean = enrollmentCoursesBean;
        this.studentsBean = studentsBean;
        this.examSeeder = examSeeder;
        this.enrollmentsBean = enrollmentsBean;
    }
    Random rn = new Random(1234);
    @Override
    protected void SeedContent() {
        List<Student> students = studentsBean.getStudents();

        for (Student student: students) {
            for(Enrollment e : student.getEnrollments()){
                final Enrollment eee = enrollmentsBean.get(e.getId());
                for (EnrollmentCourse ec: eee.getCourses()) {
                    List<Exam> exams = ec.getCourseExecution().getExams();
                    if(eee.getType().getId() == 2
                            && exams.stream().noneMatch(ex -> eee.getCurriculum().getYear().isInYear(ex.getScheduledAt()))){

                        ArrayList<CourseExecution> courseExecutions = new ArrayList<>();
                        courseExecutions.add(ec.getCourseExecution());

                        int year = eee.getCurriculum().getYear().endDate().getYear()+1900;

                        exams = new ArrayList<>();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date()); // Now use today date.
                        calendar.set(year,Calendar.JANUARY,22); // Zimsko izpitno obdobje
                        exams.addAll(examSeeder.seedExamSchedule(calendar,courseExecutions, 1,true));
                        calendar.set(year,Calendar.JULY,11); // Spomladansko izpitno obdobje
                        exams.addAll(examSeeder.seedExamSchedule(calendar,courseExecutions, 2,true));
                        calendar.set(year,Calendar.AUGUST,20); // Jesensko izpitno obdobje
                        exams.addAll(examSeeder.seedExamSchedule(calendar,courseExecutions,3, true));
                    }

                    for (Exam exam : exams) {
                        if (student.getId() != 1 || true) {
                            ExamEnrollment ee = new ExamEnrollment();
                            ee.setEnrollmentCourse(ec);
                            ee.setExam(exam);
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
                                if (exam.getCourseExecution().getId() == ec.getCourseExecution().getId()) {
                                    ee.setPastImport(true);
                                    examEnrollmentBean.add(ee);
                                    //break;
                                }
                            } else if (exam.getScheduledAt().toInstant().isBefore(Instant.now())) { // Past enrollments
                                Integer max = 100;
                                Integer min = 0;
                                Integer score = rn.nextInt(max - min + 1) + min;
                                if (exam.getId() != 76) { // Aps kononenko 2018-05-21 { }
                                    ee.setScore(score);
                                    ee.setMark(getMarkFromScore(score));
                                }

                                ee.setPastImport(true);
                                examEnrollmentBean.add(ee);
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

                            log.info("Year" + Integer.valueOf(exam.getScheduledAt().toInstant().toString().substring(0, 4)));
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

