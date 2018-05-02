package si.fri.tpo.team7.services.beans.exams;


import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.annotations.ScheduleExam;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@ApplicationScoped
@EnrollToExam
public class ExamEnrollmentBean extends EntityBean<ExamEnrollment> {

    public ExamEnrollmentBean() {
        super(ExamEnrollment.class);
    }

}

