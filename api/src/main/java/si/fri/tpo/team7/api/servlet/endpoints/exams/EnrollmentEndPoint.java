package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getEnrollments() {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();


        if (authenticatedUser.getRole() == Role.STUDENT) {
            List<ExamEnrollment> myExamEnrollments = getExamEnrollmentsForMeStudent();
            return Response.ok(myExamEnrollments).build();
        }
        else {
            return Response.ok(Response.status(501)).build(); // Not implemented
        }
    }

    public List<ExamEnrollment> getExamEnrollmentsForMeStudent () {
        List<ExamEnrollment> enrollmentList = examEnrollmentBean.get();
        List<ExamEnrollment> userEnrollments = new ArrayList<>();
        for (ExamEnrollment examEnrollment:enrollmentList) {
            Integer userId = examEnrollment.getEnrollment().getEnrollment().getToken().getStudent().getId();
            if (userId == authenticatedUser.getId()) {
                userEnrollments.add(examEnrollment);
            }
        }
        return userEnrollments;
    }

}
