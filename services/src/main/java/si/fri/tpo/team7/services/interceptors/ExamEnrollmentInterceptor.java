package si.fri.tpo.team7.services.interceptors;


import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.users.LecturersBean;
import si.fri.tpo.team7.services.beans.validators.DateValidator;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
@EnrollToExam
public class ExamEnrollmentInterceptor {
    public static final Integer MAX_DAYS_BEFORE_EXAM_APPLY = 2; // Change globally

    private Logger log = Logger.getLogger(ExamEnrollmentInterceptor.class.getName());


    @AroundInvoke
    public Object enrollToExam(InvocationContext context) throws Exception {


        String nameOfMethod = context.getMethod().getName();
        if (nameOfMethod == "add") {
            ExamEnrollment examEnrollment = (ExamEnrollment) context.getParameters()[0];

            Instant latestExamApplicationDate =  DateValidator.getLatestEnrollDismentDate(examEnrollment.getExam().getScheduledAt().toInstant());

            if (Instant.now().isAfter(latestExamApplicationDate) && !examEnrollment.isPastImport()) { // Cannot enroll in exam after the application date is over
                throw new NotFoundException( "You can no longer enroll to this exam! Lattest application date was " + latestExamApplicationDate);
            }


        } else if(nameOfMethod == "cancelEnrollment") {
            ExamEnrollment examEnrollment = (ExamEnrollment) context.getParameters()[1];

            if (examEnrollment.getStatus() != null) throw new NotFoundException("You are currently not enrolled in this exam!");

            Instant latestExamDeApplicationDate =  DateValidator.getLatestEnrollDismentDate(examEnrollment.getExam().getScheduledAt().toInstant());

            if (Instant.now().isAfter(latestExamDeApplicationDate)) { // Cannot enroll in exam after the application date is over
                throw new NotFoundException( "You can no longer cancel disenroll from this exam! Latest cancellation date was " + latestExamDeApplicationDate);
            }

        }
        return context.proceed();

    }


}
