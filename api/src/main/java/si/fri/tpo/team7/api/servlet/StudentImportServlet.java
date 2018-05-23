package si.fri.tpo.team7.api.servlet;

import si.fri.tpo.team7.services.beans.curriculum.ProgramsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentTokensBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentsBean;
import si.fri.tpo.team7.services.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.users.Student;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/v1/import-students"})
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 10485760L, // 10 MB
        maxRequestSize = 20971520L // 20 MB
)
public class StudentImportServlet extends HttpServlet {

    @Inject
    private StudentsBean studentsBean;

    @Inject
    EnrollmentTokensBean enrollmentTokensBean;

    @Inject
    ProgramsBean programsBean;

    @Inject
    EnrollmentsBean enrollmentsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>File Upload Form</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n");
        writer.append("<h1>Upload file</h1>\r\n");
        writer.append("<form method=\"POST\" action=\"import-students\" ")
                .append("enctype=\"multipart/form-data\">\r\n");
        writer.append("<input type=\"file\" name=\"fileName1\"/><br/><br/>\r\n");
        writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
        writer.append("</form>\r\n");
        writer.append("    </body>\r\n").append("</html>\r\n");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(request, response);
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, HEAD, DELETE");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

    }


    @Override
    protected void doPost(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, HEAD, DELETE");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = response.getWriter();
        for(Part part:request.getParts()){
            try {
                writer.println("[");
                Scanner sc = new Scanner(part.getInputStream());
                importStudents(writer, sc);
                writer.println("]");
            } catch (FileNotFoundException fne) {
                writer.println("You either did not specify a file to upload or are "
                        + "trying to upload a file to a protected or nonexistent "
                        + "location.");
                writer.println("<br/> ERROR: " + fne.getMessage());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
            break;
        }

    }

    public void importStudents(PrintWriter writer, Scanner scanner){
        Student s;
        boolean first = true;
        while(scanner.hasNext()){
            s = studentFromScanner(scanner);
            if(s != null) {
                if (!first) writer.println(",");
                first = false;
                writer.print(s.toJson());
            }
        }
    }

    public Student studentFromScanner(Scanner scanner)
    {
        try {
            Student student = new Student();
            String line = scanner.nextLine();
            student.setName(line.substring(0, 30).trim());
            student.setSurname(line.substring(29, 60).trim());
            int programCode = Integer.parseInt(line.substring(59, 67).trim());
            student.setEMail(line.substring(67, line.length()-1).trim());

            studentsBean.addStudentWithProgram(student, programCode);

            /*EnrollmentToken token1 = new EnrollmentToken();
            token1.setStudent(student);

            EnrollmentToken token2 = new EnrollmentToken();
            token2.setStudent(student);

            enrollmentTokensBean.add(token1);
            enrollmentTokensBean.add(token2);

            Program program = programsBean.get(programCode);

            Enrollment enrollment = new Enrollment();
            enrollment.setToken(token1);
            //enrollment.setStudyYear1(semestersBean.current());
            //enrollment.setStudyYear2(semestersBean.next());

            enrollmentsBean.add(enrollment);*/
            return student;
        }
        catch(Exception e){
            return null;
        }
    }
}