package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.exams.BEScheduleExam;
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
import java.util.*;

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
    /**
     * Get exams visible by particular role
     * */
    public Response getExams() {
        List<Exam> exams = examsBean.get();
        List<Exam> resultExams = new ArrayList<>(); // Get lecturer's courses

        List<CourseExecution> courseExecutions = courseExecutionsBean.get();
        List<Integer> resultCourseExecutionsIds = new ArrayList<>();

        if(authenticatedUser.getRole() == Role.LECTURER) {
            for (CourseExecution c : courseExecutions) {
                if (c.getLecturer1().getId() == authenticatedUser.getId()
                        || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                        || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())){
                    resultCourseExecutionsIds.add(c.getId());
                }
            }
            for (Exam e : exams) {
                CourseExecution rs = e.getCourseExecution();
                if (resultCourseExecutionsIds.contains(e.getCourseExecution().getId())) {
                    resultExams.add(e);
                }
            }
        }
        else if (authenticatedUser.getRole() == Role.CLERK || authenticatedUser.getRole() == Role.ADMIN) {
           resultExams = exams;
        } else if (authenticatedUser.getRole() == Role.STUDENT) {
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

                        resultCourseExecutionsIds.add(userEnrollment.getCourseExecution().getId());
                    }
                }
            }
            for (Exam e : exams) {
                if (resultCourseExecutionsIds.contains(e.getCourseExecution().getId())) {
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

    /**
     *
     {
     "scheduledAt": "2018-01-23T17:37:59Z",
     "courseExecution": "128"
     }
     */
    @POST
    @Secured({Role.LECTURER, Role.CLERK})
    public  Response addExam(BEScheduleExam beScheduleExam) {
        try {
            Integer courseExecutionId = beScheduleExam.getCourseExecution();
            Date scheduledAt = beScheduleExam.getScheduledAt();
            Exam exam = new Exam();
            CourseExecution courseExecution = courseExecutionsBean.get(courseExecutionId);
            if (courseExecution == null) throw new NotFoundException("Course execution is not valid!");
            exam.setCourseExecution(courseExecution);
            exam.setScheduledAt(scheduledAt);
            exam.setPastImport(false);
            exam.setWritten(false);
            examsBean.add(exam);
            return Response.ok(exam).build();
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}
