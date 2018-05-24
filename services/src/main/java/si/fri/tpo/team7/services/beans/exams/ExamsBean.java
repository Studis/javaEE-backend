package si.fri.tpo.team7.services.beans.exams;

import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.annotations.ScheduleExam;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
@ScheduleExam
public class ExamsBean extends EntityBean<Exam> {


    public ExamsBean() {
        super(Exam.class);
    }

    // SELECT distinct(exam.id),exam.scheduledat from exam,enrollmentcourse where enrollmentcourse.courseexecution=exam.courseexecution

    public List<Exam> getAvailableExamsForEnrollmentCourseId(Integer enrollmentCourseId) {
        Query q = em.createQuery("SELECT distinct(e) from Exam e,EnrollmentCourse ec where ec.courseExecution.id=e.courseExecution.id and ec.id = :enrollmentCourseId");
        q.setParameter("enrollmentCourseId", enrollmentCourseId);
        return (List<Exam>) q.getResultList();
    }

    public List<Exam> getExamsForUserId(Integer userId) {
        Query q = em.createQuery("SELECT distinct(e) from Exam e,EnrollmentCourse ec where ec.courseExecution.id=e.courseExecution.id and ec.enrollment.token.student.id = :userId");
        q.setParameter("userId", userId);
        return (List<Exam>) q.getResultList();
    }

    public List<Exam> getExamsForCourseExecution(Integer courseExecutionId) {
        Query q = em.createQuery("SELECT distinct(e) from Exam e where e.courseExecution.id=:courseExecutionId");
        q.setParameter("courseExecutionId", courseExecutionId);
        return (List<Exam>) q.getResultList();
    }


    @Transactional
    public Boolean deleteExamsForExamId(Integer examId) {


        Query q = em.createQuery("SELECT en from ExamEnrollment en where en.exam.id=:examId");
        q.setParameter("examId", examId);
        List<ExamEnrollment> examEnrollments = (List<ExamEnrollment>) q.getResultList();
        if (examEnrollments.size() == 0) {
            this.remove(examId);
            return true;
        }
        for (ExamEnrollment en : examEnrollments) {
            if (en.getDeleteConfirmed()) {

            } else {
                return false;
            }
        }
        this.remove(examId);

        return true;
    }

}
