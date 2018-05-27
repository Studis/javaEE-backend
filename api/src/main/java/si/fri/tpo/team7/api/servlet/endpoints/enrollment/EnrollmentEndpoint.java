package si.fri.tpo.team7.api.servlet.endpoints.enrollment;

import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
import si.fri.tpo.team7.services.dtos.EnrollmentResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EnrollmentEndpoint {

    @Inject
    private EnrollmentsBean enrollmentsBean;

    @GET
    @Path("get/{id}")
    public Response getEnrollment(@PathParam("id") int id){
        return Response.ok(enrollmentsBean.get(id)).build();
    }

    @GET
    @Path("{id}")
    public Response getCurriculumForToken(@PathParam("id") int tokenId){
        BEECurriculum bee = new BEECurriculum();
        Curriculum curriculumForToken = enrollmentsBean.getCurriculumForToken(tokenId);
        bee.setCurriculum(curriculumForToken);
        bee.setGeneralOptionalCourses(curriculumForToken.getGeneralOptionalCourses());
        bee.setProfessionalOptionalCourses(curriculumForToken.getProfessionalOptionalCourses());
        bee.setModules(curriculumForToken.getModules());
        bee.setObligatoryCourses(curriculumForToken.getObligatoryCourses());
        bee.setYear(curriculumForToken.getYear());
        return Response.ok(bee).build();
    }

    @POST
    @Path("{id}")
    public Response enroll(@PathParam("id") int tokenId, EnrollmentResponse enrollmentResponse){
        enrollmentsBean.enroll(tokenId, enrollmentResponse);
        return Response.ok( ).build();
    }
}


