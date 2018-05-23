package si.fri.tpo.team7.services.beans.enrollments;

import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.enums.Status;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentToken;
import si.fri.tpo.team7.services.beans.users.StudentsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotAllowedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class EnrollmentTokensBean extends EntityBean<EnrollmentToken> {
    public EnrollmentTokensBean() {
        super(EnrollmentToken.class);
    }

    @Inject
    private StudentsBean studentsBean;

    public List<EnrollmentToken> getStudentsTokens(int studentId) {
        Student student = studentsBean.getStudent(studentId);
        return student.getEnrollmentTokens();
    }

    @Transactional
    public EnrollmentToken updateToken(int tokenId, EnrollmentToken enrollmentToken) {
        EnrollmentToken token = em.find(EnrollmentToken.class, tokenId);
        if(token.getStatus().equals(Status.COMPLETED) || token.getStatus().equals(Status.DELETED)){
            throw new NotAllowedException("Editing this token is not allowed.");
        }
        token.setStatus(enrollmentToken.getStatus());
        token.setStudyType(enrollmentToken.getStudyType());
        token.setStudyForm(enrollmentToken.getStudyForm());
        token.setStudyYear(enrollmentToken.getStudyYear());
        token.setEnrollmentType(enrollmentToken.getEnrollmentType());
        token.setFreeChoice(enrollmentToken.isFreeChoice());
        em.persist(token);
        return token;
    }
    @Transactional
    public void deleteToken(int tokenId) {
        EnrollmentToken token = em.find(EnrollmentToken.class, tokenId);
        if(token.getStatus() != null) {
            if (token.getStatus().equals(Status.COMPLETED) || token.getStatus().equals(Status.DELETED)) {
                throw new NotAllowedException("Editing this token is not allowed.");
            }
        }

        token.setStatus(Status.DELETED);
        token.setUpdatedAt(new Date());
        em.remove(token);
        //em.persist(token);
    }

    @Transactional
    public EnrollmentToken addNewToken(int studentId) {
        EnrollmentToken resultToken = new EnrollmentToken();
        resultToken.setStatus(Status.ACTIVE);
        resultToken.setCreatedAt(new Date());

        Student student = studentsBean.getStudent(studentId);
        resultToken.setStudent(student);
        List<EnrollmentToken> enrollmentTokens = student.getEnrollmentTokens();
        EnrollmentToken lastToken = null;
        for (EnrollmentToken token: enrollmentTokens) {
            if (token.getStatus() != null) {
                if(token.getStatus().equals(Status.COMPLETED) || token.getStatus().equals(Status.NEW) || token.getStatus().equals(Status.ACTIVE))
                    lastToken = token;
            }
        }
        if(lastToken == null)
            throw new NotAllowedException("This user has no completed tokens!");

        //PROGRAM
        resultToken.setProgram(lastToken.getProgram());

        //ENROLLMENT TYPE //TODO ponavlja
        resultToken.setEnrollmentType(lastToken.getEnrollmentType());

        //STUDY YEAR
        if (lastToken.getStudyYear().getId() <= 2) { //TODO
            resultToken.setStudyYear(em.find(StudyYear.class, lastToken.getStudyYear().getId() + 1));
        } else {
            resultToken.setStudyYear(lastToken.getStudyYear());
        }

        //STUDY FORM
        resultToken.setStudyForm(lastToken.getStudyForm());

        //STUDY TYPE
        resultToken.setStudyType(lastToken.getStudyType());

        //FREE CHOICE
        if (lastToken.getStudyYear().getId() == 2) {//TODO
            resultToken.setFreeChoice(true);
        }

        em.persist(resultToken);
        em.flush();
        return resultToken;
    }
}
