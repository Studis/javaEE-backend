package si.fri.tpo.team7.services.beans.validators;

import javassist.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;

import java.time.Instant;
public class ExamEnrollmentValidator {
    public static boolean isDeleted(ExamEnrollment examEnrollment) { // True if exam enrollment was deleted
        return examEnrollment.getStatus() != null;
    }

    public static boolean isExamForSameCourse(ExamEnrollment a, ExamEnrollment b) {
        return a.getEnrollment().getCourseExecution().getId() == b.getExam().getCourseExecution().getId();
    }

    public static boolean scoreKnown(ExamEnrollment a) {
        return a.getScore() != null;
    }

    public static boolean markKnown(ExamEnrollment a) {
        return a.getMark() != null;
    }

    public static boolean wasExamWritten(ExamEnrollment a) {
        return DateValidator.isBefore(a.getExam().getScheduledAt().toInstant(), Instant.now());
    }

    public static boolean didIPass(ExamEnrollment a) {
        return a.getMark() != null && a.getMark() > 5;
    }

    public static boolean isSameUserEnrollment(ExamEnrollment a, ExamEnrollment b) { // If enrollments are for the token that belongs to the same user, TODO: if no token then should be payed!!!
        return (a.getEnrollment().getEnrollment().getToken().getStudent().getId() == b.getEnrollment().getEnrollment().getToken().getStudent().getId());
    }


    public static void shouldPay(ExamEnrollment pending) throws NotFoundException {
        Integer currentAttemptNumber = pending.getTotalExamAttempts()-pending.getReturnedExamAttempts() + 1;
        if (currentAttemptNumber > 3) {
            throw new javax.ws.rs.NotFoundException("This would be your " + (pending.getTotalExamAttempts() + 1) + " - " + pending.getReturnedExamAttempts() + " attempt so you have to pay for exam!");
        }

    }
}