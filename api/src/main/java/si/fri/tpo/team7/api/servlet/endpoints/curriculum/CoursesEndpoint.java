package si.fri.tpo.team7.api.servlet.endpoints.curriculum;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.exams.BEEnrollmentCourse;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.users.Lecturer;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.entities.users.User;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.time.Instant;
import java.util.*;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CoursesEndpoint {

    @Inject
    private CourseExecutionsBean courseExecutionsBean;

    @Inject
    private EnrollmentCoursesBean enrollmentCoursesBean;

    @Inject
    private ExamEnrollmentBean examEnrollmentBean;

    @Inject
    private ExamsBean examsBean;

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Secured({Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getCourses(){
        List<CourseExecution> courses = courseExecutionsBean.get();
        List<CourseExecution> resultCourses = new ArrayList<>();
        if(authenticatedUser.getRole() == Role.LECTURER) {
            for (CourseExecution c : courses) {
                if (c.getLecturer1().getId() == authenticatedUser.getId()
                        || (c.getLecturer2() != null && c.getLecturer2().getId() == authenticatedUser.getId())
                        || (c.getLecturer3() != null && c.getLecturer3().getId() == authenticatedUser.getId())){
                    resultCourses.add(c);
                }
            }
        }
        else resultCourses = courses;
        return Response.ok(resultCourses).build();
    }

    @GET
    @Secured({Role.LECTURER, Role.ADMIN, Role.CLERK, Role.STUDENT})
    @Path("{id}")
    public Response getCourse(@PathParam("id") int id){
        return Response.ok(courseExecutionsBean.get(id)).build();
    }

    @PUT
    @Path("{id}")
    public  Response updateCourse(@PathParam("id") int id, CourseExecution course) {
        return Response.ok(courseExecutionsBean.update(id, course)).build();
    }

    @GET
    //@Secured({Role.LECTURER, Role.ADMIN, Role.CLERK})
    @Path("{id}/enrollments")
    public Response getCourseEnrollments(@PathParam("id") int id){
        CourseExecution course = courseExecutionsBean.get(id);
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        for (EnrollmentCourse ec:course.getEnrollmentCourses()) {
            enrollments.add(ec.getEnrollment());
        }
        return Response.ok(enrollments).build();
    }

    @GET
    @Secured({Role.STUDENT})
    @Path("me")
    public Response getMyCourses(){ // Get courses that student is enrolled in
        Map<Integer, BEEnrollmentCourse> beEnrollmentCourseIDEnrollmentCourseMap = new HashMap();
        List<EnrollmentCourse> enrollmentCourses = enrollmentCoursesBean.get();
        Map<Integer,EnrollmentCourse> myEnrollmentCourseIdEnrollmentCourseMap = new HashMap<>();
        for (EnrollmentCourse enrollmentCourse: enrollmentCourses) { // Get enrollment courses for me
            if (enrollmentCourse.getEnrollment().getToken().getStudent().getId() == authenticatedUser.getId()) { // User has enrollment token for that course
                myEnrollmentCourseIdEnrollmentCourseMap.put(enrollmentCourse.getId(),enrollmentCourse);
                BEEnrollmentCourse beEnrollmentCourse = new BEEnrollmentCourse(); // new Business entity to set enrollment

                enrollmentCourse.setCourseExecution(courseExecutionsBean.get(enrollmentCourse.getCourseExecution().getId()));

                beEnrollmentCourse.setEnrollmentCourse(enrollmentCourse);
                beEnrollmentCourse.setExamsAvailable(examsBean.getAvailableExamsForEnrollmentCourseId(enrollmentCourse.getId()));

                beEnrollmentCourseIDEnrollmentCourseMap.put(enrollmentCourse.getId(),beEnrollmentCourse);
            }
        }

        for (ExamEnrollment examEnrollment: examEnrollmentBean.get()) {
            if (examEnrollment.getEnrollment().getEnrollment().getToken().getStudent().getId() == authenticatedUser.getId()) {
                Integer enrollmentCourseId = examEnrollment.getEnrollment().getId(); // EnrollmentCourse id from exam
                /**
                 * User in enrolled in exam if entry exist in examEnrollment and there is no mark added
                 */

                if (myEnrollmentCourseIdEnrollmentCourseMap.containsKey(enrollmentCourseId)) {
                    BEEnrollmentCourse beEnrollmentCourse = new BEEnrollmentCourse(); // new Business entity to set enrollment


                    beEnrollmentCourse.setExamEnrollment(examEnrollment);

                    beEnrollmentCourse.setEnrollmentCourse(myEnrollmentCourseIdEnrollmentCourseMap.get(enrollmentCourseId));


                    List<Exam> exams = examsBean.getAvailableExamsForEnrollmentCourseId(enrollmentCourseId);
                    for (Exam exam: exams) {
                        if (examEnrollment.getExam().getId() ==  exam.getId()) {
//                            if (examEnrollment.getMark() != null && examEnrollment.getMark() > 5) {
//                                beEnrollmentCourse.setPassed(true);
//                            }
//                            if (examEnrollment.getMark() == null) {
//                                exam.setEnrolled(examEnrollment.getStatus() == null);
//                            }
                            exam.setExamEnrollment(examEnrollment);
                        }
                    }
                    beEnrollmentCourse.setExamsAvailable(exams);



                    beEnrollmentCourseIDEnrollmentCourseMap.put(enrollmentCourseId,beEnrollmentCourse);



                }
            }
        }

        ArrayList<BEEnrollmentCourse> valuesList = new ArrayList<BEEnrollmentCourse>(beEnrollmentCourseIDEnrollmentCourseMap.values());



        return Response.ok(valuesList).build();
    }
}
