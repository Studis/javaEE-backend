package si.fri.tpo.team7.api.servlet.endpoints.users;

import si.fri.tpo.team7.beans.management.ManagementBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("management")
@Consumes({MediaType.APPLICATION_JSON,
MediaType.TEXT_PLAIN})
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ManagementEndpoint {

    @Inject
    private ManagementBean managementBean;

    //ta je text plain
    @PUT
    @Path("password")
    public Response sendNewPasswordEmail(String email){

        try {
            managementBean.sendMail(email);
        } catch (MessagingException e) {
           Response.serverError().build();
        }
        return Response.ok().build();
    }

    //ta je text plain password mislm
    @PUT
    @Path("password/{token}")
    public Response passwordReset(@PathParam("token") String token, String newPassword){
        managementBean.resetPassword(token, newPassword);
        return Response.ok().build();
    }
}
