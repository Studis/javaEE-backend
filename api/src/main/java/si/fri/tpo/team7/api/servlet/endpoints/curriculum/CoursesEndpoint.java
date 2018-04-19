package si.fri.tpo.team7.api.servlet.endpoints.curriculum;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import si.fri.tpo.team7.api.servlet.annotations.AuthenticatedUser;
import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.beans.enrollments.EnrollmentCoursesBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.users.Lecturer;
import si.fri.tpo.team7.entities.users.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import si.fri.tpo.team7.entities.users.User;

@Path("/courses")
@CrossOrigin
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CoursesEndpoint {

    @Inject
    private CoursesBean coursesBean;

    @Inject
    private EnrollmentCoursesBean enrollmentCoursesBean;

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @GET
    @Secured({Role.ADMIN, Role.LECTURER, Role.CLERK})
    public Response getCourses(){
        List<Course> courses = coursesBean.get();
        List<Course> resultCourses = new ArrayList<>();
        if(authenticatedUser.getRole() == Role.LECTURER) {
            for (Course c : courses) {
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
        return Response.ok(coursesBean.get(id)).build();
    }

    @PUT
    @Path("{id}")
    public  Response updateCourse(@PathParam("id") int id, Course course) {
        return Response.ok(coursesBean.update(id, course)).build();
    }

    @GET
    //@Secured({Role.LECTURER, Role.ADMIN, Role.CLERK})
    @Path("{id}/enrollments")
    public Response getCourseEnrollments(@PathParam("id") int id){
        Course course = coursesBean.get(id);
        return Response.ok(course.getEnrollmentCourses()).build();
    }

    @GET
    //@Secured({Role.LECTURER, Role.ADMIN, Role.CLERK})
    @Path("{id}/enrollments/csv")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCourseEnrollmentsCsv(@PathParam("id") int id){
        Course course = coursesBean.get(id);
        Set<EnrollmentCourse> courses = course.getEnrollmentCourses();
        String csv = "";
        int cnt = 1;
        for (EnrollmentCourse i:courses) {
            Student s = i.getEnrollment().getToken().getStudent();
            EnrollmentType et = i.getEnrollment().getType();
            csv += cnt+","+s.getEnrollmentNumber()+","+s.getSurname()+","+s.getName()+",";
            csv += et.getId()+"-"+et.getName()+"\n";
            cnt++;
        }

        return Response.ok(csv).build();
    }

    @GET
    //@Secured({Role.LECTURER, Role.ADMIN, Role.CLERK})
    @Path("{id}/enrollments/pdf")
    @Produces("application/pdf")
    public Response getCourseEnrollmentsPdf(@PathParam("id") int id){
        Course course = coursesBean.get(id);
        Set<EnrollmentCourse> courses = course.getEnrollmentCourses();

        try{
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType0Font.load(doc, new File("arial.ttf"));

            try (PDPageContentStream contents = new PDPageContentStream(doc, page))
            {
                contents.beginText();
                contents.setFont(font, 14);
                contents.newLineAtOffset(100, 700);
                contents.showText(course.getName());
                contents.setFont(font, 12);

                boolean first = true;
                String out = "";
                for(Lecturer l:course.getLecturers()){
                    if(!first) out += ",";
                    first = false;
                    out += " "+l.getName()+" "+l.getSurname()+" ";

                }
                contents.newLineAtOffset(10, -20);
                contents.showText(out);

                contents.newLineAtOffset(-10, -20);
                int cnt = 1;
                for (EnrollmentCourse i:courses) {
                    Student s = i.getEnrollment().getToken().getStudent();
                    contents.newLineAtOffset(0, -20);
                    EnrollmentType et = i.getEnrollment().getType();
                    contents.showText(cnt+". "+s.getEnrollmentNumber()+" "+s.getSurname()+" "+s.getName()+" "+et.getId()+"-"+et.getName());
                    cnt++;
                }
                contents.endText();
            }
            doc.save(Instant.now().getEpochSecond()+".pdf");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        File file = new File(Instant.now().getEpochSecond()+".pdf");

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=export.pdf");
        return response.build();
    }
}
