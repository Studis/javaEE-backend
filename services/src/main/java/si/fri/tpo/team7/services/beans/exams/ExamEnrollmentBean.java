package si.fri.tpo.team7.services.beans.exams;

import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.beans.validators.DateValidator;
import si.fri.tpo.team7.services.beans.validators.ExamEnrollmentValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            obj.setPastImport(false);
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
     * @param examEnrollments
     * @return boolean
     * @throws NotFoundException
     */
    public boolean existingEnrollmentsMiddleWare(List<ExamEnrollment> examEnrollments, ExamEnrollment pending) throws NotFoundException {
        Integer examAttemptsInCurrentYear = 0;
        Integer totalExamAttempts = 0;
        Map<ExamEnrollment,Integer> mapExamEnrollmentAttempts = new HashMap<>();
        for (ExamEnrollment examenrollment: examEnrollments) {

            if (ExamEnrollmentValidator.isDeleted(examenrollment)) continue; // Do not count exam enrollment that was disenrolled in time (has set status to deleted) in the correct due date


            Integer pendingUserId = pending.getEnrollment().getEnrollment().getToken().getStudent().getId();
            Integer currentUserId = examenrollment.getEnrollment().getEnrollment().getToken().getStudent().getId();

            Integer pendingExecutionId = pending.getExam().getCourseExecution().getId();
            Integer examEnrollmentExecutionId = examenrollment.getExam().getCourseExecution().getId();


            Boolean userIsEnrolledToSameCourse = pendingUserId == currentUserId && pendingExecutionId == examEnrollmentExecutionId;


            if (pending.getEnrollment().getCourseExecution().getId() != pending.getExam().getCourseExecution().getId()) {
                throw new NotFoundException("Exam term does not exist for given enrollment course!");
            }

//            if (pendingUserId == currentUserId && pending.getExam().getCourseExecution().getId() == examenrollment.getExam().getCourseExecution().getId()) { // Here is the right course execution - TESTing
//                throw new NotFoundException("Exam enrollment for exam " + pending.getEnrollment().getCourseExecution().getCourse().getName() + " already exist! for user " + pendingUserId);
//            } // TESTING

            // If exam has not yet been written you cannot add mark to it
            if (((pending.getScore() != null) || (pending.getMark() != null)) && pending.getExam().getScheduledAt().after(new Date())) {
                throw new NotFoundException("Exam has not yet been written, you cannot add marks or scores to future exams!");
            }

            if (ExamEnrollmentValidator.isSameUserEnrollment(examenrollment,pending) && ExamEnrollmentValidator.isExamForSameCourse(examenrollment,pending)) { // If it is enrollment for the same user

                log.info("Yes");
                // If user is already enrolled in the exam with same course execution and has not received mark yet
                if (!ExamEnrollmentValidator.markKnown(examenrollment)) {
                    throw new NotFoundException("You can't enroll before you receive final mark!");
                }
                // If user is already enrolled in the exam with same course execution and has completed it
                if (ExamEnrollmentValidator.didIPass(examenrollment)) {
                    throw new NotFoundException("You have already completed this exam!");
                }
                if (examenrollment.getExam().getId() == pending.getExam().getId()) {
                    throw new NotFoundException("Enrollment to this exam already exist!");
                }
                // Duration between exam attempts must be at least DURATION_BETWEEN_EXAM_ATTEMPTS days!
                // TODO: check if working
                if (Math.abs(DateValidator.durationBetweenDatesInDays(examenrollment.getExam().getScheduledAt().toInstant(),
                        pending.getExam().getScheduledAt().toInstant())) <= DURATION_BETWEEN_EXAM_ATTEMPTS) {
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
                throw new NotFoundException("You will need to pay for exam due to total attempt " + totalExamAttempts+1);
            }

            Integer typeOfStudy = pending.getEnrollment().getEnrollment().getStudyType().getId();

            if (typeOfStudy == 3 && !pending.getPaid()) {
                throw new NotFoundException("You will need to pay for exam!");
            }

        }

        return true;
    }


    @Transactional
    public ExamEnrollment cancelEnrollment(int id, ExamEnrollment s) { // deletedBy is set in endpoint
        ExamEnrollment obj = em.find(ExamEnrollment.class, id);
        if(obj == null) {
            throw new NotFoundException(ExamEnrollment.class.getName() + " " + id + " not found.");
        }
        s.setUpdatedAt(new Date());
        s.setStatus("deleted");
        s.setId(id);
        return em.merge(s);
    }

    @Override
    @Transactional
    public void remove(int id) {
        throw new NotFoundException("Use cancelEnrollment method!");
    }

}

