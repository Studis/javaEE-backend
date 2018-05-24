package si.fri.tpo.team7.services.interceptors;

import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.annotations.ScheduleExam;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.validators.DateValidator;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
@ScheduleExam
public class ScheduleExamInterceptor {
    private Logger log = Logger.getLogger(ExamsBean.class.getName());

    @AroundInvoke
    public Object scheduleExam(InvocationContext context) throws Exception {

        String nameOfMethod = context.getMethod().getName();
        if (nameOfMethod == "add") {
            Exam obj = (Exam)context.getParameters()[0];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(obj.getScheduledAt());
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                throw new NotFoundException( "Exam is not allowed to be scheduled on weekends");
            }
            if (obj.getScheduledAt().before(new Date()) && !obj.isPastImport()) {
                throw new NotFoundException( "Exam is not allowed to be scheduled in the past");
            }
            // Uncomment time constraint if needed
//            if (calendar.get(Calendar.HOUR_OF_DAY) < 6 || calendar.get(Calendar.HOUR_OF_DAY) > 21) {
//                throw new NotFoundException("Exam cannot be writen at that hour, please select time between 6 and 21");
//            }
        }
        return context.proceed();

    }
}
