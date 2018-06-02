package si.fri.tpo.team7.services.beans.exams;

import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.annotations.EnrollToExam;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.services.beans.validators.DateValidator;
import si.fri.tpo.team7.services.beans.validators.ExamEnrollmentValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.util.*;
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
        obj = existingEnrollmentsMiddleWare(super.get(),obj);
        if (obj != null) {
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
     * @param examEnrollments
     * @param pending
     * @throws NotFoundException
     */

    public ExamEnrollment existingEnrollmentsMiddleWare(List<ExamEnrollment> examEnrollments, ExamEnrollment pending) throws NotFoundException {


        // DEFINITIONS  (^_^) ******************************************************************************************************************************************************************************

        Map<Integer,Integer> mapCourseIdToAttempts = new HashMap<>();
        Map<Integer,Integer> mapCourseIdToYearAttempts = new HashMap<>();
        Integer pendingExecutionId = pending.getExam().getCourseExecution().getId();


        // CHECKS FOR PENDING  (^_^) ******************************************************************************************************************************************************************************

        if (!ExamEnrollmentValidator.isExamForSameCourse(pending,pending)) { // Validation for frontend if right exam! RED flag
            throw new NotFoundException("Exam term does not exist for given enrollment course!");
        }

        if (((pending.getScore() != null) || (pending.getMark() != null)) && pending.getExam().getScheduledAt().after(new Date())) { // Cannot add marks or scores to future exams
            throw new NotFoundException("Exam has not yet been written, you cannot add marks or scores to future exams!");
        }

        // PAST EXAM ENROLLMENTS  (^_^) ******************************************************************************************************************************************************************************

        for (ExamEnrollment examenrollment: examEnrollments) {

            if (ExamEnrollmentValidator.isDeleted(examenrollment)) continue; // Do not count exam enrollment that were deleted in time (has set status to deleted) in the correct due date

//            if (Instant.now().isAfter(examenrollment.getExam().getScheduledAt().toInstant()) && !pe.isPastImport()) { // Cannot enroll in exam after the application date is over
//                throw new NotFoundException( "You can no longer enroll to this exam! Lattest application date was " + latestExamApplicationDate);
//            }

            Integer examEnrollmentExecutionId = examenrollment.getExam().getCourseExecution().getId(); // Helper fields

            if (ExamEnrollmentValidator.isSameUserEnrollment(examenrollment,pending) && ExamEnrollmentValidator.isExamForSameCourse(examenrollment,pending)) { // If it is enrollment for the same user

                // If user is already enrolled in the exam with same course execution and has not received mark yet
//                if (!pending.isPastImport() && !ExamEnrollmentValidator.markKnown(examenrollment)) {
//                    throw new NotFoundException("You can't enroll before you receive final mark for last attempts!");
//                }

                // If user is already enrolled in the exam with same course execution and has completed it
                if (!pending.isPastImport() && ExamEnrollmentValidator.didIPass(examenrollment)) {
                    throw new NotFoundException("You have already completed exam for " +
                            ExamEnrollmentValidator.getCourseTitle(examenrollment) + ", you recived " + examenrollment.getMark());
                }



        /*
            END BEAUTIFUL CODE
        */

                // Če zdej ponavljam in najdem redni type na trenutnemu TODO: this ain't workin
//                if (pending.getEnrollmentCourse().getEnrollment().getType().getId() == 2 && examenrollment.getEnrollmentCourse().getEnrollment().getType().getId() == 1) {
//                    pending.setReturnedExamAttempts(pending.getReturnedExamAttempts()+1);
//                }

                if (mapCourseIdToAttempts.get(examenrollment.getExam().getCourseExecution().getId()) != null) {
                    mapCourseIdToAttempts.put(examenrollment.getExam().getCourseExecution().getId(), 1 + mapCourseIdToAttempts.get(examenrollment.getExam().getCourseExecution().getId()));
                } else {
                    mapCourseIdToAttempts.put(examenrollment.getExam().getCourseExecution().getId(), 1);
                }

                if (!pending.isPastImport() && pending.getExam().getCourseExecution().getYear() !=
                       examenrollment.getEnrollmentCourse().getEnrollment().getCurriculum().getYear() && pending.getPaid() == null) { // Student is not enrolled
                    throw new NotFoundException( "You need to pay for the exam, you are no longer enrolled!");
                }


                log.info(examenrollment
                        .getEnrollmentCourse()
                        .getEnrollment()
                        .getCurriculum()
                        .getYear()
                        .getId() + " "+ (examenrollment.getExam().getScheduledAt().getYear()+1900));

                if (mapCourseIdToYearAttempts.get(examEnrollmentExecutionId) != null
                        && examenrollment
                        .getEnrollmentCourse()
                        .getEnrollment()
                        .getCurriculum()
                        .getYear()
                        .getId() == examenrollment.getEnrollmentCourse().getCourseExecution().getYear().getId()) {
                    mapCourseIdToYearAttempts.put(examEnrollmentExecutionId,mapCourseIdToYearAttempts.get(pendingExecutionId)+1);
                } else {
                    mapCourseIdToYearAttempts.put(examenrollment.getExam().getCourseExecution().getId(),1);

                }




            }

//            Integer examenrollmentStudyYear = examenrollment.getEnrollmentCourse().getEnrollment().getCurriculum().getStudyYear().getId();
//            Integer pendingStudyYear = pending.getEnrollmentCourse().getEnrollment().getCurriculum().getStudyYear().getId();

            // Total exam attempts in study year is 3
//            if (userIsEnrolledToSameCourse && examenrollmentStudyYear == pendingStudyYear) {
//                examAttemptsInCurrentYear++;
//            }

            // Total exam attempts max is 6
//            if (userIsEnrolledToSameCourse) {
//            }

            // If user has already written exam 6 times then he is not allowed to write it again
//            if (totalExamAttempts == 6) {
//                throw new NotFoundException("You have already written exam 6 times and are not allowed to enroll again");
//            }

            // If user has already written exam 3 times in the current study year then he is not allowed to write it again
//            if (!pending.isPastImport() && examAttemptsInCurrentYear == 3) {
//                throw new NotFoundException("You have already written exam 3 in current study year and are not allowed to enroll again this year");
//            }



            Integer typeOfStudy = pending.getEnrollmentCourse().getEnrollment().getStudyType().getId();

            if (typeOfStudy == 3 && pending.getPaid() == null) {
                pending.setPaid(true);
                throw new NotFoundException("You will need to pay for exam!");
            }

        }
        Iterator it = mapCourseIdToAttempts.entrySet().iterator();



        while (it.hasNext()) { // V študijskem letu
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
//            if ((Integer)pair.getValue()+1 >= 3) throw new NotFoundException("You have already written exam 3 times in this study year"); // rollback
            if ((Integer)pair.getKey() == pending.getExam().getCourseExecution().getId()) {

                pending.setTotalExamAttempts(((Integer)pair.getValue())+1);
                if (pending.getTotalExamAttempts() == 0) {
                    pending.setTotalExamAttempts(1);
                }
            }



            it.remove(); // avoids a ConcurrentModificationException
        }

        Iterator it2 = mapCourseIdToAttempts.entrySet().iterator();


        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
//            if ((Integer)pair.getValue() >= 3) throw new NotFoundException("You have already written exam 3 times in this study year"); // rollback
            if ((Integer)pair.getKey() == pending.getExam().getCourseExecution().getId()) {
                pending.setAttemptsInCurrentStudyYear(((Integer)pair.getValue())+1);
            }
            it2.remove(); // avoids a ConcurrentModificationException
        }



        if (pending.getTotalAttempts() > 6) {
             throw new NotFoundException("You have already written exam 6 times and are not allowed to enroll again!");
        }


        if (pending.getTotalExamAttempts() > 3) { // rollback TODO: !!!!!!!
            pending.setPaid(true);
//            throw new NotFoundException("You can't write exam more than 3 times in study year!");
        }

//        ExamEnrollment previos = examEnrollments.stream() // TODO: This does not work!!!!!
//                .min(Comparator.comparing(p -> p.getExam().getScheduledAt().toInstant()
//                        .isBefore(pending.getExam().getScheduledAt().toInstant())
//                        && p.getExam().getId() != pending.getExam().getId()
//                && p.getMark() != null && p.getMark() == 5)).orElse(null);

        Long minDur = Long.MAX_VALUE;
        ExamEnrollment thatEnrollment = null;
        for (ExamEnrollment examEnrollment : examEnrollments) {
                if (ExamEnrollmentValidator.markKnown(examEnrollment) &&
                        ExamEnrollmentValidator.isSameUserEnrollment(examEnrollment,pending)
                        && ExamEnrollmentValidator.isExamForSameCourse(examEnrollment,pending)) {
                    Long tmpDur = ExamEnrollmentValidator.durationBetweenExamEnrollmentsInDays(pending,examEnrollment);
                    if (tmpDur < minDur) {
                        minDur = tmpDur;
                        thatEnrollment = examEnrollment;
                    }
                }
            }
        if (!pending.isPastImport() && minDur < DURATION_BETWEEN_EXAM_ATTEMPTS) { // It is working
            throw new NotFoundException( "Duration between attempts " + ExamEnrollmentValidator.getExamScheduled(thatEnrollment)
                    + " and " + ExamEnrollmentValidator.getExamScheduled(pending)
                    +  " must be at least " +  DURATION_BETWEEN_EXAM_ATTEMPTS + " days!");
        }



        // Duration between exam attempts must be at least DURATION_BETWEEN_EXAM_ATTEMPTS days! TODO: check
//        if (previos != null) log.info("Duration between attempts " + ExamEnrollmentValidator.getExamScheduled(previos)
//                + " and " + ExamEnrollmentValidator.getExamScheduled(pending)
//                +  " must be at least " +  ExamEnrollmentValidator.durationBetweenExamEnrollmentsInDays(previos,pending)+ " days!");
//        if (!pending.isPastImport() && previos != null
//                && ExamEnrollmentValidator.durationBetweenExamEnrollmentsInDays(previos,pending) <= 93) {
//            throw new NotFoundException( "Duration between attempts " + ExamEnrollmentValidator.getExamScheduled(previos)
//                    + " and " + ExamEnrollmentValidator.getExamScheduled(pending)
//                    +  " must be at least " +  93+ " days!");
//        }



        return pending;
    }


    @Transactional
    public ExamEnrollment cancelEnrollment(int id, ExamEnrollment s,User deletedBy) { // deletedBy is set in endpoint
        ExamEnrollment obj = em.find(ExamEnrollment.class, id);
        if(obj == null) {
            throw new NotFoundException(ExamEnrollment.class.getName() + " " + id + " not found.");
        }
        obj.setUpdatedAt(new Date());
        obj.setStatus("deleted");
        obj.setId(id);
        obj.setDeletedBy(deletedBy.getId());
        return em.merge(obj);
    }

    @Override
    @Transactional
    public void remove(int id) {
        throw new NotFoundException("Use cancelEnrollment method!");
    }

}

