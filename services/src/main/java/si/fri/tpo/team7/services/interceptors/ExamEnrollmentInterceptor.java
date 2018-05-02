package si.fri.tpo.team7.services.interceptors;


import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
@EnrollToExam
public class ExamEnrollmentInterceptor {
    private Logger log = Logger.getLogger(ExamsBean.class.getName());
    private final Integer MAX_DAYS_BEFORE_EXAM_APPLY = 2;

    @AroundInvoke
    public Object enrollToExam(InvocationContext context) throws Exception {

        String nameOfMethod = context.getMethod().getName();
        if (nameOfMethod == "add") {
            ExamEnrollment examEnrollment = (ExamEnrollment) context.getParameters()[0];

            Instant now = Instant.now();
            Instant latestExamApplicationDate =  examEnrollment.getExam().getScheduledAt().toInstant().truncatedTo(ChronoUnit.DAYS).plus(23,ChronoUnit.HOURS).plus(55,ChronoUnit.MINUTES).minus(MAX_DAYS_BEFORE_EXAM_APPLY, ChronoUnit.DAYS); // Truncate

            if (now.isAfter(latestExamApplicationDate)) { // Cannot enroll in exam after the application date is over
                throw new NotFoundException( "You can no longer enroll to this exam!");
            }
            


        }
        return context.proceed();

    }
}
