package si.fri.tpo.team7.api.servlet.endpoints.users;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.entities.users.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

@Path("/students")
@CrossOrigin
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class StudentEndpoint {

    @Inject
    private StudentsBean studentsBean;

    @GET
    public Response getStudents(){
        return Response.ok(studentsBean.getStudents()).build();
    }

    @GET
    @Secured({Role.STUDENT})
    @Path("{id}")
    public Response getStudent(@PathParam("id") int id){
        return Response.ok(studentsBean.getStudent(id)).build();
    }

    @POST
    public  Response addStudent(Student student){
        Student s = studentsBean.addStudent(student);

        return Response.status(Response.Status.CREATED).entity(s).build();
    }

    @PUT
    @Path("{id}")
    public  Response updateStudent(@PathParam("id") int id, Student student) {

        return Response.ok(studentsBean.updateStudent(id, student)).build();
    }

    @PUT
    @Path("{id}/enroll")
    public  Response enrollStudent(@PathParam("id") int id, String course) {

        return Response.ok(studentsBean.enrollStudent(id, course)).build();
    }

    @DELETE
    @Path("{id}")
    public  Response removeStudent(@PathParam("id") int id) {
        studentsBean.removeStudent(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Secured({Role.STUDENT})
    @Path("me")
    public Response getMe(){
        return Response.ok("{\"id\":\""+authenticatedUser.getId()+"\", \"role\": \""+authenticatedUser.getRole()+"\"}").build();
    }
}
