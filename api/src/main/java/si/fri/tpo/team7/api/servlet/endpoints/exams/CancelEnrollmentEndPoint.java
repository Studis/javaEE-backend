package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.BEEnrollmentExam;
import si.fri.tpo.team7.entities.exams.BEExamCancelEnrollment;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/exams/enrollments/cancel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CancelEnrollmentEndPoint {

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @Inject
    ExamEnrollmentBean examEnrollmentBean;



    @POST
    @Secured({Role.STUDENT, Role.LECTURER, Role.CLERK})
    public Response cancelEnrollment(BEExamCancelEnrollment beExamCancelEnrollment) { // Pass ExamEnrollmentId



            if (authenticatedUser.getRole() == Role.STUDENT) { // Return all current enrollments to exams
                ExamEnrollment examEnrollment = isMyEnrollment(beExamCancelEnrollment.getExamEnrollmentId());
                examEnrollmentBean.cancelEnrollment(examEnrollment.getId(), examEnrollment);
                return Response.ok(examEnrollment).build();
            } else if (authenticatedUser.getRole() == Role.CLERK) { // TODO: finnish, not part of this userStory
                ExamEnrollment examEnrollment = getCorrectExamEnrollment(beExamCancelEnrollment.getExamEnrollmentId());
                if (examEnrollment != null) {
                    examEnrollmentBean.cancelEnrollment(examEnrollment.getId(), examEnrollment);
                }
                examEnrollmentBean.cancelEnrollment(examEnrollment.getId(),examEnrollment);
                return Response.ok(examEnrollment).build();
            } else if (authenticatedUser.getRole() == Role.LECTURER) { // TODO: finnish, not part of this userStory
                ExamEnrollment examEnrollment = isExamEnrollmentOfMyCourse(beExamCancelEnrollment.getExamEnrollmentId());
                if (examEnrollment != null) {
                    examEnrollmentBean.cancelEnrollment(examEnrollment.getId(), examEnrollment);
                }
                return Response.ok(examEnrollment).build();
            } else {
                return Response.ok("Invalid role").build();
            }
    }

    public ExamEnrollment isMyEnrollment (int examEnrollmentId) {
        ExamEnrollment examEnrollment = getCorrectExamEnrollment(examEnrollmentId);
        if (examEnrollment.getEnrollment().getEnrollment().getToken().getStudent().getId() == authenticatedUser.getId()) {
            return examEnrollment;
        }
        throw new NotFoundException("You do not have permission to cancel this enrollment!!");
    }

    public ExamEnrollment isExamEnrollmentOfMyCourse (int examEnrollmentId) {
        ExamEnrollment examEnrollment = getCorrectExamEnrollment(examEnrollmentId);
        if (examEnrollment.getEnrollment().getCourseExecution().getLecturer1().getId() == authenticatedUser.getId()
                || examEnrollment.getEnrollment().getCourseExecution().getLecturer2().getId() == authenticatedUser.getId()
                || examEnrollment.getEnrollment().getCourseExecution().getLecturer3().getId() == authenticatedUser.getId()) {
            return examEnrollment;
        }
        throw new NotFoundException("You do not have permission to cancel this enrollment!");
    }

    public ExamEnrollment getCorrectExamEnrollment (int examEnrollmentId) {
        ExamEnrollment examEnrollment = examEnrollmentBean.get(examEnrollmentId);
        if (examEnrollment == null) {
            throw new NotFoundException("ExamEnrollment with this id does not exist");
        }
        examEnrollment.setDeletedBy(authenticatedUser.getId());
        return examEnrollment;
    }

}
