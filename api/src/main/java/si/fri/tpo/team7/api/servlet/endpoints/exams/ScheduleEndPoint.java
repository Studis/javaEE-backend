package si.fri.tpo.team7.api.servlet.endpoints.exams;

import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/exams/scheduled")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ScheduleEndPoint {

    @Inject
    private ExamsBean examsBean;

    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getExams(){
        List<Exam> exams = examsBean.get();
        return Response.ok(exams).build();
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
