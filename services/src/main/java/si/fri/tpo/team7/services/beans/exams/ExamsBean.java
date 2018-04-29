package si.fri.tpo.team7.services.beans.exams;

import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.annotations.ScheduleExam;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
@ScheduleExam
public class ExamsBean extends EntityBean<Exam> {

    public ExamsBean() {
        super(Exam.class);
    }

}
