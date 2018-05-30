package si.fri.tpo.team7.services.beans.validators;

import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.entities.exams.ExamEnrollment;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ExamEnrollmentValidator {
    public static boolean isDeleted(ExamEnrollment examEnrollment) { // True if exam enrollment was deleted
        return examEnrollment.getStatus() != null;
    }

    public static boolean isExamForSameCourse(ExamEnrollment a, ExamEnrollment b) {
        return a.getEnrollmentCourse().getCourseExecution().getId() == b.getExam().getCourseExecution().getId();
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
        return (a.getEnrollmentCourse().getEnrollment().getToken().getStudent().getId() == b.getEnrollmentCourse().getEnrollment().getToken().getStudent().getId());
    }

    public static String getCourseTitle(ExamEnrollment a) { // If enrollments are for the token that belongs to the same user, TODO: if no token then should be payed!!!
        return a.getExam().getCourseExecution().getCourse().getName();
    }

    public static Long durationBetweenExamEnrollmentsInDays(ExamEnrollment first, ExamEnrollment second) { // negative value if tomorrow, today
        return Math.abs(DateValidator.durationBetweenDatesInDays(first.getExam().getScheduledAt().toInstant(),second.getExam().getScheduledAt().toInstant()));
    }
    public static Instant getExamScheduled(ExamEnrollment first) { // negative value if tomorrow, today
        return first.getExam().getScheduledAt().toInstant();
    }
}