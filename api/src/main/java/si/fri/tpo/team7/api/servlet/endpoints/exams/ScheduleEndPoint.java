package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/exams/scheduled")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ScheduleEndPoint {

    @Inject
    private CourseExecutionsBean courseExecutionsBean;

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

        List<CourseExecution> courses = courseExecutionsBean.get();
        List<Course> resultCourses = new ArrayList<>();

        if(authenticatedUser.getRole() == Role.LECTURER) {
            for (CourseExecution c : courses) {
                if (c.getLecturer1().getId() == authenticatedUser.getId()
                        || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                        || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())){
                    resultCourses.add(coursesBean.get(c.getCourse().getId()));
                }
            }
            System.out.println(resultCourses);
            for (Exam e : exams) {
                if (resultCourses.contains(e.getCourse())) {
                    resultExams.add(e);
                }
            }
        }
        else if (authenticatedUser.getRole() == Role.CLERK || authenticatedUser.getRole() == Role.ADMIN) {
           resultExams = exams;
        } else if (authenticatedUser.getRole() == Role.STUDENT) { //TODO:  Check if user is enrolled

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
