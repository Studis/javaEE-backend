package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.entities.enums.Status;
import si.fri.tpo.team7.entities.location.Residence;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CurriculumsBean;
import si.fri.tpo.team7.services.beans.pojo.ResidencesBean;
import si.fri.tpo.team7.services.beans.users.MunicipalitiesBean;
import si.fri.tpo.team7.services.beans.users.StudentsBean;
import si.fri.tpo.team7.services.dtos.EnrollmentResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import java.util.*;


@ApplicationScoped
public class EnrollmentsBean extends EntityBean<Enrollment> {
    @Inject
    private CurriculumsBean curriculumsBean;

    @Inject
    private EnrollmentTokensBean enrollmentTokensBean;

    @Inject
    private CourseExecutionsBean courseExecutionsBean;

    @Inject
    private StudentsBean studentsBean;

    @Inject
    private MunicipalitiesBean municipalitiesBean;

    @Inject
    private ResidencesBean residencesBean;

    public EnrollmentsBean() {
        super(Enrollment.class);
    }

    public EnrollmentResponse getEnrollmentData( int studentId) {
//        Student student = studentsBean.getStudent(studentId);
//        List<EnrollmentToken> enrollmentTokens = student.getEnrollmentTokens();
//        EnrollmentToken lastToken = null;
//        for (EnrollmentToken token: enrollmentTokens) {
//            if (token.getStatus() != null) {
//                if(token.getStatus().equals(Status.COMPLETED) || token.getStatus().equals(Status.ACTIVE))
//                    lastToken = token;
//            }
//        }
//        if(lastToken == null)
//            throw new NotAllowedException("This user has no completed tokens!");
//
//        Curriculum c = curriculumsBean.get(token.getProgram(),
//                em.find(Year.class, Calendar.getInstance().get(Calendar.YEAR)),
//                token.getStudyYear());
//        curriculumsBean.loadCourses(courseExecutionsBean,c);
//        return c;
        return null;
    }
    public Curriculum getCurriculumForToken( int tokenId) {
        EnrollmentToken token = enrollmentTokensBean.get(tokenId);

        Curriculum c = curriculumsBean.get(token.getProgram(),
                em.find(Year.class, Calendar.getInstance().get(Calendar.YEAR)),
                token.getStudyYear());
        curriculumsBean.loadCourses(courseExecutionsBean,c);
        return c;
    }


    @Transactional
    public void enroll(int tokenId, EnrollmentResponse enrollmentResponse) {
        EnrollmentToken token = enrollmentTokensBean.get(tokenId);
        token.setStatus(Status.ACTIVE);


        //ENROLLMENT
        Enrollment enrollment = new Enrollment();
        enrollment.setToken(token);



        //PROGRAM, STUDY YEAR, YEAR => CIRRICULUM
        Curriculum c = curriculumsBean.get(enrollmentResponse.getProgram(),
                em.find(Year.class, Calendar.getInstance().get(Calendar.YEAR)),
                enrollmentResponse.getStudyYear());
        enrollment.setCurriculum(c);


        //STUDY TYPE
        enrollment.setStudyType(enrollmentResponse.getStudyType());


        //STUDY FORM
        enrollment.setStudyForm(enrollmentResponse.getStudyForm());


        //ENROLLMENT FORM
        enrollment.setType(enrollmentResponse.getEnrollmentType());


        token.setEnrollment(enrollment);
        em.persist(enrollment);

        //COURSES
        List<EnrollmentCourse> courses = new ArrayList<>();
        for (int courseExecution:enrollmentResponse.getCourses()) {
            CourseExecution ce = em.find(CourseExecution.class, courseExecution);
            EnrollmentCourse enrollmentCourse = new EnrollmentCourse();
            enrollmentCourse.setCourseExecution(ce);
            enrollmentCourse.setEnrollment(enrollment);
            em.persist(enrollmentCourse);
            em.flush();
            courses.add(enrollmentCourse);
        }
        enrollment.setCourses(courses);

        em.persist(enrollment);


        //STUDENT
        Student student = token.getStudent();
        Student requestStudent = enrollmentResponse.getStudent();


        //1st COL
        //DATE OF BIRTH
        student.setDateOfBirth(requestStudent.getDateOfBirth());

        //COUNTRY
        student.setNationality(requestStudent.getNationality());

        //GENDER
        student.setGender(requestStudent.getGender());

        //TAX NUMBER
        student.setTaxNumber(requestStudent.getTaxNumber());

        //PHONE NUMBER
        student.setPhoneNumber(requestStudent.getPhoneNumber());


        //2nd COL
        //NAME
        student.setName(requestStudent.getName());

        //SURNAME
        student.setSurname(requestStudent.getSurname());

        //PLACE OF BIRTH
        student.setPlaceOfBirth(requestStudent.getPlaceOfBirth());

        //REGION
        student.setRegion(requestStudent.getRegion());

        //EMSO
        student.setEmso(requestStudent.getEmso());

        //NASLOV ZA VROCANJE
        student.setSendToTemporary(requestStudent.isSendToTemporary());

        //EMAIL
        student.setEMail(requestStudent.getEMail());
        em.persist(student);


        //PERMANENT RESIDENCE
        Residence permanentResidence;
        if(student.getPermanent() != null ){
           permanentResidence = student.getPermanent();
        } else {
            Residence r = new Residence();
            residencesBean.add(r);
            permanentResidence = r;
            student.setPermanent(r);
        }

        Residence permanentResidenceRequest = requestStudent.getPermanent();

        //COUNTRY
        permanentResidence.setCountry(permanentResidenceRequest.getCountry());
        //MUNICIPALITY
        if(permanentResidenceRequest.getCountry().equals("SI")) {
            permanentResidence.setMunicipality(municipalitiesBean.getMunicipalityByName(permanentResidenceRequest.getMunicipality().getName()));
        }
        //POSTAL NUMBER
        permanentResidence.setPostalNumber(permanentResidenceRequest.getPostalNumber());
        //ADDRESS
        permanentResidence.setPlaceOfResidence(permanentResidenceRequest.getPlaceOfResidence());
        em.persist(permanentResidence);


        //Temporary RESIDENCE
        Residence temporaryResidence;
        if(student.getTemporary() != null ){
            temporaryResidence = student.getTemporary();
        } else {
            Residence r = new Residence();
            residencesBean.add(r);
            temporaryResidence = r;
            student.setTemporary(r);
        }
        Residence temporaryResidenceRequest = requestStudent.getTemporary();

        //COUNTRY
        temporaryResidence.setCountry(temporaryResidenceRequest.getCountry());
        //MUNICIPALITY
        if(temporaryResidenceRequest.getCountry().equals("SI")){
            temporaryResidence.setMunicipality(municipalitiesBean.getMunicipalityByName(temporaryResidenceRequest.getMunicipality().getName()));
        }
        //POSTAL NUMBER
        temporaryResidence.setPostalNumber(temporaryResidenceRequest.getPostalNumber());
        //ADDRESS
        temporaryResidence.setPlaceOfResidence(temporaryResidenceRequest.getPlaceOfResidence());
        em.persist(temporaryResidence);

        for (EnrollmentToken t : student.getEnrollmentTokens()) {
            if (t.getStatus() != null && t.getStatus().equals(Status.OPEN))
                enrollmentTokensBean.deleteToken(t.getId());
        }

        return;//todo return enrollment when u fix json stack overflow
    }
}
