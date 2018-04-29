package si.fri.tpo.team7.services.beans.users;

import si.fri.tpo.team7.services.beans.curriculum.ProgramsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentTokensBean;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.users.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@ApplicationScoped
public class StudentsBean {
    private Logger log = Logger.getLogger(StudentsBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Inject
    EnrollmentTokensBean enrollmentTokensBean;

    @Inject
    ProgramsBean programsBean;

    @Transactional
    public List<Student> getStudents() {

        return em.createNamedQuery("Student.getAll", Student.class).getResultList();
    }

    @Transactional
    public Student getStudent(int id){
        Student student = em.find(Student.class, id);
        if(student == null) {
            throw new NotFoundException("Student " + id + " not found.");
        }
        em.refresh(student);
        return student;
    }

    public class MyResponseRequestWrapper extends HttpServletResponseWrapper {
        public MyResponseRequestWrapper(HttpServletResponse response) {
            super(response);
        }
    }

    @Transactional
    public Student studentFromScanner(Scanner scanner)
    {
        try {
            Student student = new Student();
            student.setName(scanner.next());
            student.setSurname(scanner.next());

            EnrollmentToken token1 = new EnrollmentToken();
            token1.setStudent(student);

            EnrollmentToken token2 = new EnrollmentToken();
            token2.setStudent(student);

            em.persist(token1);
            em.persist(token2);

            Program program = programsBean.get(scanner.nextInt());

            Enrollment enrollment = new Enrollment();
            enrollment.setToken(token1);
            //enrollment.setStudyYear1(semestersBean.current());
            //enrollment.setStudyYear2(semestersBean.next());

            em.persist(enrollment);

            scanner.next();
            student.setEMail(scanner.next());
            return student;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Student addStudent(Student s) {
        if(s == null){
            return null;
        }

        em.persist(s);
        em.flush();
        s.setUsername(generateUsername(s));
        s.setPassword(s.getUsername());
        s.setEnrollmentNumber(generateEnrollmentNum(s));
        return s;
    }

    private String generateUsername(Student s) {
        String firstLetter = s.getName().substring(0,1).toLowerCase();
        String secondLetter = s.getSurname().substring(0,1).toLowerCase();
        String id = Integer.toString(s.getId());

        int count = id.length();
        for(int i = count; i<4; i++){
            id = "0" + id;
        }

        return firstLetter + secondLetter + id;
    }

    private String generateEnrollmentNum(Student s) {
        int facultyNumber = 63;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) - 2000;
        int id = s.getId();

        return String.valueOf((facultyNumber * 100 + currentYear) * 10000 + id);
    }

    @Transactional
    public void removeStudent(int id) {
        Student student = em.find(Student.class, id);
        if(student == null) {
            throw new NotFoundException("Student " + id + " not found.");
        }
        em.remove(student);
    }

    @Transactional
    public Student updateStudent(int id, Student s) {
        Student student = em.find(Student.class, id);
        if(student == null) {
            throw new NotFoundException("Student " + id + " not found.");
        }
        s.setId(id);
        em.merge(s);
        return s;
    }

    @Transactional
    public  Student enrollStudent(int id, String course){
        Student student = em.find(Student.class, id);
        return student;
    }

}
