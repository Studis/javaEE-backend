package si.fri.tpo.team7.services.beans.exams;


import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.annotations.ScheduleExam;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@EnrollToExam
public class ExamEnrollmentBean extends EntityBean<ExamEnrollment> {
    private Logger log = Logger.getLogger(ExamsBean.class.getName());
    private final Integer DURATION_BETWEEN_EXAM_ATTEMPTS = 14;

    public ExamEnrollmentBean() {
        super(ExamEnrollment.class);
    }

    @Override
    @Transactional
    public ExamEnrollment add(ExamEnrollment obj) {
        if(obj == null){
            return null;
        }

        if (existingEnrollmentsMiddleWare(super.get(),obj)) {
            obj.setCreatedAt(new Date());
            em.persist(obj);
            em.flush();
            return obj;
        }
        return null;
    }


    /**
     *
     * @param examEnrollments - List of all exam enrollments
     * @param pending
     * @return
     * @throws NotFoundException
     */
    public boolean existingEnrollmentsMiddleWare(List<ExamEnrollment> examEnrollments, ExamEnrollment pending) throws NotFoundException {
        Integer examAttemptsInCurrentYear = 0;
        Integer totalExamAttempts = 0;
        for (ExamEnrollment examenrollment: examEnrollments) {

            Integer pendingUserId = pending.getEnrollment().getEnrollment().getToken().getStudent().getId();
            Integer currentUserId = examenrollment.getEnrollment().getEnrollment().getToken().getStudent().getId();

            Integer pendingExecutionId = pending.getExam().getCourseExecution().getId();
            Integer examEnrollmentExecutionId = examenrollment.getExam().getCourseExecution().getId();


            Boolean userIsEnrolledToSameCourse = pendingUserId == currentUserId && pendingExecutionId == examEnrollmentExecutionId;

            if (pending.getEnrollment().getCourseExecution().getId() != pending.getExam().getCourseExecution().getId()) {
                throw new NotFoundException("Exam term does not exist for given enrollment course!");

            }

            // If exam has not yet been written you cannot add mark to it
            if (((pending.getScore() != null) || (pending.getMark() != null)) && pending.getExam().getScheduledAt().after(new Date())) {
                throw new NotFoundException("Exam has not yet been written, you cannot add marks or scores to future exams!");
            }

            // If user is already enrolled in the exam with same course execution and has not received mark yet
            if (userIsEnrolledToSameCourse && examenrollment.getMark() == null) {
                throw new NotFoundException("You can't enroll before you receive final mark!");
            }

            // If user is already enrolled in the exam with same course execution and has completed it
            if (userIsEnrolledToSameCourse && examenrollment.getMark() > 5) {
                throw new NotFoundException("You have already completed this exam!");
            }

            // Duration between exam attempts must be at least DURATION_BETWEEN_EXAM_ATTEMPTS days!
            if (userIsEnrolledToSameCourse && examenrollment.getExam().isWritten() && examenrollment.getExam().getId() != pending.getExam().getId()) {
                Instant firstExam = examenrollment.getExam().getScheduledAt().toInstant();
                Instant secondExam = pending.getExam().getScheduledAt().toInstant();

                if (Duration.between(firstExam,secondExam).toDays() <= DURATION_BETWEEN_EXAM_ATTEMPTS) {
                    log.info(firstExam.toString() + " " + secondExam.toString());
                    throw new NotFoundException( "Duration between attempts must be " + DURATION_BETWEEN_EXAM_ATTEMPTS + " days!");
                }
            }

            Integer examenrollmentStudyYear = examenrollment.getEnrollment().getEnrollment().getCurriculum().getStudyYear().getId();
            Integer pendingStudyYear = pending.getEnrollment().getEnrollment().getCurriculum().getStudyYear().getId();


            // Total exam attempts in study year is 3
            if (userIsEnrolledToSameCourse && examenrollmentStudyYear == pendingStudyYear) {
                examAttemptsInCurrentYear++;
            }

            // Total exam attempts max is 6
            if (userIsEnrolledToSameCourse) {
                totalExamAttempts++;
            }

            // If user has already written exam 6 times then he is not allowed to write it again
            if (totalExamAttempts == 6) {
                throw new NotFoundException("You have already written exam 6 times and are not allowed to enroll again");
            }

            // If user has already written exam 3 times in the current study year then he is not allowed to write it again
            if (examAttemptsInCurrentYear == 3) {
                throw new NotFoundException("You have already written exam 3 in current study year and are not allowed to enroll again this year");
            }

            if (totalExamAttempts >= 3 && !pending.getPaid()) {
                throw new NotFoundException("You need to pay for exam due to total attempt " + totalExamAttempts+1 +" in order to enroll!");
            }

            Integer typeOfStudy = pending.getEnrollment().getEnrollment().getStudyType().getId();

            if (typeOfStudy == 3 && !pending.getPaid()) {
                throw new NotFoundException("You need to pay for exams in order to enroll! Please settle the paynment!");
            }
            
        }


        return true;

    }

}

