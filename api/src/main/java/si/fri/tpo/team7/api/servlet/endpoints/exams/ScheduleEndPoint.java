package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentTokensBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/exams/scheduled")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ScheduleEndPoint {

    @Inject
    private CourseExecutionsBean courseExecutionsBean;

    @Inject
    private EnrollmentTokensBean enrollmentTokensBean;

    @Inject
    private EnrollmentCoursesBean enrollmentCoursesBean;

    @Inject
    private EnrollmentsBean enrollmentsBean;

    @Inject
    private CoursesBean coursesBean;

    @Inject
    private ExamsBean examsBean;

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getExams() {
        List<Exam> exams = examsBean.get();
        List<Exam> resultExams = new ArrayList<>(); // Get lecturer's courses

        List<CourseExecution> courseExecutions = courseExecutionsBean.get();
        List<CourseExecution> resultCourseExecutions = new ArrayList<>();

        if(authenticatedUser.getRole() == Role.LECTURER) {
            for (CourseExecution c : courseExecutions) {
                if (c.getLecturer1().getId() == authenticatedUser.getId()
                        || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                        || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())){
                    resultCourseExecutions.add(c);
                }
            }
            for (Exam e : exams) {
                if (resultCourseExecutions.contains(e.getCourseExecution())) {
                    resultExams.add(e);
                }
            }
        }
        else if (authenticatedUser.getRole() == Role.CLERK || authenticatedUser.getRole() == Role.ADMIN) {
           resultExams = exams;
        } else if (authenticatedUser.getRole() == Role.STUDENT) { //TODO:  Check if student is enrolled
            Integer userId = authenticatedUser.getId();
            List<EnrollmentToken> enrollmentTokens = enrollmentTokensBean.get(); // get tokens of user
            for (EnrollmentToken enrollmentToken: enrollmentTokens) {
                if (enrollmentToken.getStudent().getId() == userId) { // Get enrollments for that user

                    List<Enrollment> userEnrollments = new ArrayList<>(); // User enrollments


                    List<Enrollment> allEnrollments = enrollmentsBean.get(); // User enrollments
                    for (Enrollment oneEnrollment: allEnrollments) {
                        if (oneEnrollment.getToken().getId() == (enrollmentToken.getId())) {
                            userEnrollments.add(oneEnrollment);
                        }
                    }
                    // correct

                    List<EnrollmentCourse> enrollmentCourses = enrollmentCoursesBean.get();

                    List<EnrollmentCourse> userEnrollmentCourses = new ArrayList<>();


                    for (EnrollmentCourse enrollmentCourse: enrollmentCourses) {
                        if (userEnrollments.contains(enrollmentCourse.getEnrollment())) {
                            userEnrollmentCourses.add(enrollmentCourse);
                        }
                     }

                    // Iterate overy user enrollments
                    for (EnrollmentCourse userEnrollment: userEnrollmentCourses) {

                        // CourseExecution ce = enrollmentCoursesBean.get(userEnrollment.getId()).getCourseExecution();

                        resultCourseExecutions.add(userEnrollment.getCourseExecution());
                    }
                }
            }
            for (Exam e : exams) {
                if (resultCourseExecutions.contains(e.getCourseExecution())) {
                    resultExams.add(e);
                }
            }


        }
        return Response.ok(resultExams).build();
    }

    @GET
    @Secured({Role.LECTURER, Role.ADMIN, Role.CLERK, Role.STUDENT})
    @Path("{id}")
    public Response getExam(@PathParam("id") int id){
        return Response.ok(examsBean.get(id)).build();
    }

    @PUT
    @Secured({Role.LECTURER, Role.CLERK})
    @Path("{id}")
    public  Response updateExam(@PathParam("id") int id, Exam exam) {
        return Response.ok(examsBean.update(id, exam)).build();
    }

    @POST
    @Secured({Role.LECTURER, Role.CLERK})
    public  Response addExam(Exam exam) {
        examsBean.add(exam);
        return Response.ok(exam).build();
    }

}
