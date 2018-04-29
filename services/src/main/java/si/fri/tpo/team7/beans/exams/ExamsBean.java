package si.fri.tpo.team7.beans.exams;

import com.sun.tools.corba.se.idl.constExpr.Not;
import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
public class ExamsBean extends EntityBean<Exam> {
    private Logger log = Logger.getLogger(ExamsBean.class.getName());

    public ExamsBean() {
        super(Exam.class);
    }

    @Override
    @Transactional
    public Exam add(Exam obj) {
        if(obj == null){
            return null;
        }
        // Middleware to check if exam can be scheduled
        if (isScheduleDateValid(obj)) {
            obj.setCreatedAt(new Date());
            em.persist(obj);
            em.flush();
            return obj;
        } else {
            return null;
        }
    }

    public boolean isScheduleDateValid (Exam obj) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(obj.getScheduledAt());
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            log.severe("Exam is not allowed to be scheduled on weekends");
            return false;
        }
        if (obj.getScheduledAt().before(new Date()) && !obj.isPastImport()) {
            log.severe("Exam is not allowed to be scheduled in the past");
            return false;
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 6 && calendar.get(Calendar.HOUR_OF_DAY) > 21) {
            log.severe("Exam cannot be writen at that hour, please select time between 6 and 21");
            return false;
        }
        return true;
    }
}
