package si.fri.tpo.team7.services.beans.users;

import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.enrollments.*;
import si.fri.tpo.team7.entities.enums.Status;
import si.fri.tpo.team7.services.beans.curriculum.ProgramsBean;
import si.fri.tpo.team7.services.beans.enrollments.EnrollmentTokensBean;
import si.fri.tpo.team7.entities.curriculum.Program;
import si.fri.tpo.team7.entities.users.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
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
    public Student addStudent(Student s) {
        if(s == null){
            return null;
        }


        em.persist(s);
        em.flush();
        s.setUsername(generateUsername(s));
        s.setPassword(s.getUsername());
        s.setEnrollmentNumber(generateEnrollmentNum(s));

        EnrollmentToken token = new EnrollmentToken();
        token.setStatus(Status.NEW);
        token.setStudyYear(em.find(StudyYear.class, 1));
        token.setStudyForm(em.find(StudyForm.class, 1));
        token.setStudyType(em.find(StudyType.class, 1));
        token.setEnrollmentType(em.find(EnrollmentType.class,1));
        token.setProgram(programsBean.get(1000475));
        token.setFreeChoice(false);
        token.setStudent(s);


        List<EnrollmentToken> tokens = new ArrayList<>();
        tokens.add(token);
        s.setEnrollmentTokens(tokens);
        em.persist(token);
        em.persist(s);
        em.flush();
        return s;
    }
    @Transactional
    public Student addStudentWithProgram(Student s, int programCode) {
        if(s == null){
            return null;
        }


        em.persist(s);
        em.flush();
        s.setUsername(generateUsername(s));
        s.setPassword(s.getUsername());
        s.setEnrollmentNumber(generateEnrollmentNum(s));

        EnrollmentToken token = new EnrollmentToken();
        token.setStatus(Status.NEW);
        token.setStudyYear(em.find(StudyYear.class, 1));
        token.setStudyForm(em.find(StudyForm.class, 1));
        token.setStudyType(em.find(StudyType.class, 1));
        token.setEnrollmentType(em.find(EnrollmentType.class,1));
        token.setProgram(programsBean.get(programCode));
        token.setFreeChoice(false);
        token.setStudent(s);


        List<EnrollmentToken> tokens = new ArrayList<>();
        tokens.add(token);
        s.setEnrollmentTokens(tokens);
        em.persist(token);
        em.persist(s);
        em.flush();
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
        return em.merge(s);
    }

    @Transactional
    public  Student enrollStudent(int id, String course){
        Student student = em.find(Student.class, id);
        return student;
    }

}
