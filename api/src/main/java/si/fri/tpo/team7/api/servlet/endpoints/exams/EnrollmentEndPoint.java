package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.BEEnrollmentExam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;

@Path("/exams/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EnrollmentEndPoint {

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
    public Response getEnrollments() {

        if (authenticatedUser.getRole() == Role.STUDENT) { // Return all current enrollments to exams
            List<ExamEnrollment> myExamEnrollments = getExamEnrollmentsForMeStudent();
            return Response.ok(myExamEnrollments).build();
        } else if (authenticatedUser.getRole() == Role.CLERK) {
            return Response.ok(allEnrollments()).build();
        } else if (authenticatedUser.getRole() == Role.LECTURER) {
            return Response.ok(enrolledToExamForMyCourse()).build();
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
            examEnrollment.setPaid(beEnrollmentExam.getPaid());
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
                    if (examEnrollment.getMark() == null) { // He has not received mark for that enrollment
                        userEnrollments.add(examEnrollment);
                    }
                }
            }
        }
        return userEnrollments;
    }

    public List<ExamEnrollment> allEnrollments () {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        return enrollmentList;
    }

    public List<ExamEnrollment> enrolledToExamForMyCourse () {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        List<ExamEnrollment> myExamEnrollments = new ArrayList<>();
        for (ExamEnrollment examEnrollment: enrollmentList) {
            CourseExecution c = examEnrollment.getEnrollment().getCourseExecution();

            if (c.getLecturer1().getId() == authenticatedUser.getId() // If i am executing this course
                    || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                    || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())){
                myExamEnrollments.add(examEnrollment);
            }
        }
        return myExamEnrollments;
    }

    public boolean doNotShowArchivedEnrollments(ExamEnrollment examEnrollment) { // Not deleted
        return examEnrollment.getStatus() == null;
    }

}
