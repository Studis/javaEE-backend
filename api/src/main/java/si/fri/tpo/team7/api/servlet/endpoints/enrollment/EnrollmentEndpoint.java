package si.fri.tpo.team7.api.servlet.endpoints.enrollment;

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

    @POST
    @Path("{id}")
    public Response enroll(@PathParam("id") int tokenId, EnrollmentResponse enrollmentResponse){
        enrollmentsBean.enroll(tokenId, enrollmentResponse);
        return Response.ok( ).build();
    }
}
