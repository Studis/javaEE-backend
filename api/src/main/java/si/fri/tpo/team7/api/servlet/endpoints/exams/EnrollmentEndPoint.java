package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.BEEnrollmentExam;
import si.fri.tpo.team7.entities.exams.BEExamResults;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.services.beans.validators.DateValidator;
import si.fri.tpo.team7.services.beans.validators.ExamValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.RescaleOp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("/exams/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EnrollmentEndPoint {
    Logger log = Logger.getLogger(EnrollmentEndPoint.class.getName());

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @Inject
    ExamEnrollmentBean examEnrollmentBean;

    @Inject
    ExamsBean examsBean;

    @Inject
    EnrollmentCoursesBean enrollmentCoursesBean;

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    @Path("{courseExecutionId}")
    public Response getEnrollments(@PathParam("courseExecutionId") Integer courseExecutionId) {
        return Response.ok(enrolledToExamForMyCourse(courseExecutionId)).build();
    }


    @PUT
    @Path("{examEnrollmentId}")
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response updateEnrollment(@PathParam("examEnrollmentId") int examEnrollmentId, BEExamResults examResults) {
        ExamEnrollment nov = examEnrollmentBean.get(examEnrollmentId);
        if (examResults==null) throw new NotFoundException("Malformed request!");

        if (nov != null) {
            if (nov.getExam() == null) throw new NotFoundException("Exam not found for this enrollment!");
            nov.setExam(examsBean.get(nov.getExam().getId()));

            Integer score = examResults.getScore();
            if (score!= null) {
                if (DateValidator.isAfter(nov.getExam().getScheduledAt().toInstant(), Instant.now())) {
                    throw new NotFoundException("You cannot add results before exam is written!");
                }
                if (Math.abs(DateValidator.durationBetweenDatesInDays(Instant.now(),nov.getExam().getScheduledAt().toInstant())) > 31) {
                    throw new NotFoundException("You can't change exam score after more than 31 days!");
                }
                if (score < 0 || score > 100) throw new NotFoundException("Score must be between 0 and 100");
                nov.setScore(score);
            }
            Integer mark = examResults.getMark();
            if (mark != null) {
                if (DateValidator.isAfter(nov.getExam().getScheduledAt().toInstant(), Instant.now())) {
                    throw new NotFoundException("You cannot add mark before exam is written!");
                }
                if (Math.abs(DateValidator.durationBetweenDatesInDays(Instant.now(),nov.getExam().getScheduledAt().toInstant())) > 31) {
                    throw new NotFoundException("You can't change exam results after more than 31 days!");
                }
                if (mark < 1 || mark > 10) throw new NotFoundException("Mark must be between 1 and 10");
                nov.setMark(mark);

            }
            if (nov.getStatus() == null && examResults.getCancelEnrollment() != null) {
                if (DateValidator.isBefore(nov.getExam().getScheduledAt().toInstant(),
                        Instant.now())) {
                    throw new NotFoundException("You can no longer delete exam application! Exam was already written!");

                } else {
                    nov = examEnrollmentBean.cancelEnrollment(examEnrollmentId,nov,authenticatedUser.getId());

                }
            }


        } else {
            throw new NotFoundException("Exam enrollment with such id does not exist!");
        }



        return Response.ok(examEnrollmentBean.update(examEnrollmentId,nov)).build();
    }

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getEnrollments() {

        if (authenticatedUser.getRole() == Role.STUDENT) { // Return all current enrollments to exams
            List<ExamEnrollment> myExamEnrollments = getExamEnrollmentsForMeStudent();
            return Response.ok(myExamEnrollments).build();
        } else if (authenticatedUser.getRole() == Role.CLERK) {
            return Response.ok(allEnrollments()).build();
        } else if (authenticatedUser.getRole() == Role.LECTURER) {
            return Response.ok(enrolledToExamForMyCourse(null)).build();
        }
        else {
            return Response.ok("Not implemented").build(); // Not implemented
        }
    }

    /**
     * Enrollment to the exam endpoint, this is applicable to students or // TODO: clerk - #23
     *{
     * 	"enrollmentCourseId": "12",
     * 	"examId": "120"
     * }
     */
    @POST
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response addEnrollment(BEEnrollmentExam beEnrollmentExam) {

        if (authenticatedUser.getRole() == Role.STUDENT) { // Return all current enrollments to exams
            ExamEnrollment examEnrollment = new ExamEnrollment();

            examEnrollment.setExam(examsBean.get(beEnrollmentExam.getExamId()));

            Integer userEnrollmentCourseId = enrollmentCoursesBean.get(beEnrollmentExam.getEnrollmentCourseId()).getEnrollment().getToken().getStudent().getId();
            if (userEnrollmentCourseId != authenticatedUser.getId()) throw new NotFoundException("You are not enrolled in this course");

            examEnrollment.setEnrollment(enrollmentCoursesBean.get(beEnrollmentExam.getEnrollmentCourseId()));
            examEnrollment.setPaid(false); // examEnrollment.setPaid(beEnrollmentExam.getPaid());


            // TODO: just 2 TESTING code
            if (examEnrollment.getEnrollment().getCourseExecution().getId() != examEnrollment.getExam().getCourseExecution().getId()) {
                throw new NotFoundException("Exam is not for the same course execution!");
            }

            if (examEnrollment.getEnrollment().getEnrollment().getToken().getStudent().getId() != authenticatedUser.getId()) {
                throw new NotFoundException("Oopps you to enroll other people than yourself!");
            }

            examEnrollmentBean.add(examEnrollment);
            return Response.ok(examEnrollment).build();
        } else if (authenticatedUser.getRole() == Role.CLERK) {
            return Response.ok(Response.status(501)).build(); // Not implemented in this userstory, !doNotShowArchivedEnrollments
        } else {
            return Response.ok(authenticatedUser.getRole() + " cannot enroll to exam!").build();
        }
    }

    public List<ExamEnrollment> getExamEnrollmentsForMeStudent () {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        List<ExamEnrollment> userEnrollments = new ArrayList<>();
        for (ExamEnrollment examEnrollment:enrollmentList) {
            if (doNotShowArchivedEnrollments(examEnrollment)) {
                Integer userId = examEnrollment.getEnrollment().getEnrollment().getToken().getStudent().getId();
                if (userId == authenticatedUser.getId()) {
                    // if (examEnrollment.getMark() == null) { // He has not received mark for that enrollment
                        userEnrollments.add(examEnrollment);
                    // }
                }
            }
        }
        return userEnrollments;
    }

    public List<ExamEnrollment> allEnrollments () {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        return enrollmentList;
    }

    public List<ExamEnrollment> enrolledToExamForMyCourse (Integer enrolledToExamForMyCourse) {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        List<ExamEnrollment> myExamEnrollments = new ArrayList<>();
        for (ExamEnrollment examEnrollment: enrollmentList) {
            if (true || doNotShowArchivedEnrollments(examEnrollment)) {
                CourseExecution c = examEnrollment.getEnrollment().getCourseExecution();

                if (enrolledToExamForMyCourse == null && c.getLecturer1().getId() == authenticatedUser.getId() // If i am executing this course
                        || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                        || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())) {
                    myExamEnrollments.add(examEnrollment);
                }
                else if (enrolledToExamForMyCourse != null) {
                    if (examEnrollment.getEnrollment().getCourseExecution().getId() == enrolledToExamForMyCourse) {
                        myExamEnrollments.add(examEnrollment);
                    }
                }
            }
        }
        return myExamEnrollments;
    }

    public boolean doNotShowArchivedEnrollments(ExamEnrollment examEnrollment) { // Not deleted
        return examEnrollment.getStatus() == null;
    }

}
