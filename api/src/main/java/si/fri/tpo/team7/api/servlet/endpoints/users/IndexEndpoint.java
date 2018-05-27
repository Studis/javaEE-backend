package si.fri.tpo.team7.api.servlet.endpoints.users;

import lombok.Data;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
import si.fri.tpo.team7.services.beans.users.StudentsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/index")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IndexEndpoint {

    @Inject
    StudentsBean studentsBean;

    @Inject
    EnrollmentsBean enrollmentsBean;

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    @Path("/{id}/last")
    public Response getEnrollments(@PathParam("id") Integer studentId) {
        return Response.ok(getIndex(studentId, false)).build();
    }

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    @Path("/{id}/all")
    public Response getEnrollmentsAll(@PathParam("id") Integer studentId) {
        return Response.ok(getIndex(studentId, true)).build();
    }

    private List<BEEEnrollment> getIndex(int studentId, boolean all){
        ArrayList<BEEEnrollment> beeEnrollments = new ArrayList<>();
        Student student = studentsBean.getStudent(studentId);
        List<Enrollment> enrollments = student.getEnrollments();

        for (Enrollment e : enrollments) {
            e = enrollmentsBean.get(e.getId());
            BEEEnrollment beee = new BEEEnrollment();
            beee.setEnrollment(e);
            ArrayList<BEEIndex> indices = new ArrayList<>();

            List<EnrollmentCourse> courses = e.getCourses();
            for (EnrollmentCourse ec: courses) {
                List<ExamEnrollment> examEnrollments = ec.getExamEnrollments();

                if(examEnrollments.size() > 0) {
                    examEnrollments = examEnrollments.stream()
                            .filter(p -> p.getMark() != null)
                            .sorted(Comparator.comparing(o -> o.getExam().getScheduledAt()))
                            .collect(Collectors.toList());
                    if (all) {
                        for (ExamEnrollment ee:examEnrollments) {
                            BEEIndex index = new BEEIndex();
                            index.setCourseExecution(ec.getCourseExecution());
                            index.setExamEnrollment(ee);
                            indices.add(index);
                        }
                    }
                    else{
                        BEEIndex index = new BEEIndex();
                        index.setCourseExecution(ec.getCourseExecution());
                        index.setExamEnrollment(examEnrollments.get(examEnrollments.size()-1));
                        indices.add(index);
                    }
                }
                else{
                    BEEIndex index = new BEEIndex();
                    index.setCourseExecution(ec.getCourseExecution());
                    indices.add(index);
                }
            }
            beee.setIndex(indices);
            beeEnrollments.add(beee);
        }

        return beeEnrollments;
    }
}

@Data
class BEEEnrollment{
    Enrollment enrollment;
    List<BEEIndex> index;
}

@Data
class BEEIndex{
    CourseExecution courseExecution;
    ExamEnrollment examEnrollment;
}
