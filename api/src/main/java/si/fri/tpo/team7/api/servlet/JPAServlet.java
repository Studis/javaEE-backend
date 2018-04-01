package si.fri.tpo.team7.api.servlet;

import si.fri.tpo.team7.beans.StudentsBean;
import si.fri.tpo.team7.entities.users.Student;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {


    @Inject
    private StudentsBean studentsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student s = new Student();
        s.setName("Bob");
        s.setSurname("Klobasa");

        try (PrintWriter writer = resp.getWriter()) {
            writer.append("Test");
            studentsBean.addStudent(s);
            List<Student> students = studentsBean.getStudents();
            Student student1 = studentsBean.getStudent(1);
            writer.println("STUDENT 1: " + student1);
            for (Student student: students) {
                writer.println(student.toString());
            }
        }
    }
}