package si.fri.tpo.team7.services.beans.validators;


import si.fri.tpo.team7.services.interceptors.ExamEnrollmentInterceptor;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateValidator {

    // True if first is after second
    public static boolean isAfter(Instant one, Instant two) {
        return one.isAfter(two);
    }
    // True if first is before second
    public static boolean isBefore(Instant one, Instant two) {
        return one.isBefore(two);
    }
    // Duration between dates 23.5.2018 and 24.5.2018 is 1
    public static Long durationBetweenDatesInDays(Instant first, Instant second) { // negative value if tomorrow, today
        return Duration.between(first.truncatedTo(ChronoUnit.DAYS),second.truncatedTo(ChronoUnit.DAYS)).toDays();
    }
    public static Instant trunateToDays(Instant first) {
        return first.truncatedTo(ChronoUnit.DAYS);
    }
    // Convert to day and add 23.55
    public static Instant dayTo2355 (Instant first) {
        return first.truncatedTo(ChronoUnit.DAYS).plus(23,ChronoUnit.HOURS).plus(55,ChronoUnit.MINUTES); // .minus(MAX_DAYS_BEFORE_EXAM_APPLY, ChronoUnit.DAYS); // Truncate
    }
    // Subtract days from date
    public static Instant subtractDays(Instant first, Integer days) {
        return first.minus(days, ChronoUnit.DAYS);
    }
    // Add days from date
    public static Instant addDays(Instant first, Integer days) {
        return first.plus(days, ChronoUnit.DAYS);
    }
    // getLatestEnrolmentDate/disEnrollment
    public static Instant getLatestEnrollDismentDate(Instant examScheduledAt) {
        return subtractDays(dayTo2355(examScheduledAt), ExamEnrollmentInterceptor.MAX_DAYS_BEFORE_EXAM_APPLY);
    }
}
