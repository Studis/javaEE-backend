package si.fri.tpo.team7.api.servlet.endpoints.tokens;

import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentTokensBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("tokens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class EnrollmentTokenEndpoint {

    @Inject
    private EnrollmentTokensBean enrollmentTokensBean;

    @GET
    @Path("{id}")
    public Response getEnrollmentTokenForStudent(@PathParam("id") int studentId) {
        List<EnrollmentToken> tokens = enrollmentTokensBean.getStudentsTokens(studentId);
        return Response.ok(tokens).build();
    }

    @POST
    @Path("{id}")
    public Response addNewToken(@PathParam("id") int studentId) {
        EnrollmentToken token = enrollmentTokensBean.addNewToken(studentId);
        return Response.ok(token).build();
    }

    @PUT
    @Path("{id}")
    public Response updateToken(@PathParam("id") int tokenId, EnrollmentToken token) {
        EnrollmentToken t = enrollmentTokensBean.updateToken(tokenId, token);
        return Response.ok(t).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteToken(@PathParam("id") int tokenId) {
        enrollmentTokensBean.deleteToken(tokenId);
        return Response.ok().build();
    }
}
