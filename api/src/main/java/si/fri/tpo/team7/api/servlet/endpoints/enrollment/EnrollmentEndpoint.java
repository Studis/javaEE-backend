package si.fri.tpo.team7.api.servlet.endpoints.enrollment;

import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.Module;
import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
import si.fri.tpo.team7.services.dtos.EnrollmentResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
        List<Module> moduleCourseList = curriculumForToken.getModules();
        List<ModuleCourse> moduleCourses = new ArrayList<>();
        for (Module m : moduleCourseList) {
            if (m.getCurriculum().getId() == curriculumForToken.getId()) {
                for (ModuleCourse mm : m.getCourses()) {
                    moduleCourses.add(mm);
                }
            }
        }


        bee.setModuleCourses(moduleCourses);
        bee.setObligatoryCourses(curriculumForToken.getObligatoryCourses());
        bee.setYear(curriculumForToken.getYear());
        return Response.ok(bee).build();
    }

    @POST
    @Path("{id}")
    public Response enroll(@PathParam("id") int tokenId, EnrollmentResponse enrollmentResponse){
        return Response.ok( enrollmentsBean.enroll(tokenId, enrollmentResponse)).build();
    }
}


