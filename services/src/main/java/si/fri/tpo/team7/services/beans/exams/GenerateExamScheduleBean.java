package si.fri.tpo.team7.services.beans.exams;

import si.fri.tpo.team7.entities.curriculum.*;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.curriculum.*;
import si.fri.tpo.team7.services.beans.pojo.ExamScheduleDates;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class GenerateExamScheduleBean {

    @Inject
    private CurriculumsBean curriculumsBean;

    @Inject
    private ExamsBean examsBean;

    @Inject
    private ProgramsBean programsBean;

    @Inject
    private YearsBean yearsBean;

    @Inject
    private StudyYearsBean studyYearsBean;

    /**
     * Schedules exams for a given program for a given study year.
     *
     */
    public void generateExamSchedule( int program, ExamScheduleDates dates) {
        for (int studyYear = 1; studyYear<= 3; studyYear++) {
            Program p = programsBean.get(program);


            Curriculum c = curriculumsBean.get(p,
                    yearsBean.get(dates.year),
                    studyYearsBean.get(studyYear)
            );

            List<CourseExecution> ce = new ArrayList<>();
            c.getObligatoryCourses().forEach((item) -> ce.add(item));
            c.getProfessionalOptionalCourses().forEach((item) -> ce.add(item));
            c.getGeneralOptionalCourses().forEach((item) -> ce.add(item));
            for (Module m : c.getModules()) {
                m.getCourses().forEach((item) -> ce.add(item));
            }
            Collections.shuffle(ce);


            List<CourseExecution> ceW = new ArrayList<>();
            List<CourseExecution> ceS = new ArrayList<>();
            for (CourseExecution temp : ce) {
                if (temp.isWinter())
                    ceW.add(temp);
                else
                    ceS.add(temp);
            }
            linkDates(LocalDate.parse(dates.winterStart),
                    LocalDate.parse(dates.winterEnd), ceW);
            linkDates(LocalDate.parse(dates.springStart),
                    LocalDate.parse(dates.springEnd), ceS);

            linkDatesAutum(LocalDate.parse(dates.autumnStart),
                    LocalDate.parse(dates.autumnEnd), ce);

            /*linkDates(LocalDate.of(year, Month.JANUARY, 22),
                    LocalDate.of(year, Month.FEBRUARY, 16), ceW);
            linkDates(LocalDate.of(year, Month.JUNE, 11),
                    LocalDate.of(year, Month.JULY, 6), ceS);

            linkDatesAutum(LocalDate.of(year, Month.AUGUST, 20),
                    LocalDate.of(year, Month.SEPTEMBER, 14), ce);*/
        }

    }

    private void linkDates(LocalDate start, LocalDate end,  List<CourseExecution> ce) {
        List<Date> winter = getDatesBetweenWithoutWeekend(start,end);
        int numOfDaysInWinter = getDatesBetween(start,end).size();
        int spacing = (int) Math.floor(numOfDaysInWinter/ (ce.size()*2));
        for(int i = 0; i < ce.size(); i++ ){
            CourseExecution temp = ce.get(i);

            Exam e = new Exam();
            e.setCourseExecution(temp);
            e.setAsking(temp.getLecturer1().getName() + " " + temp.getLecturer1().getSurname());
            int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            if(randomNum == 1)
                e.setLocation("PA");
            else if(randomNum == 2)
                e.setLocation("PB");
            else
                e.setLocation("P22");
            e.setExamTerm(1);
            e.setWritten(true);
            e.setScheduledAt(winter.get(i*spacing));
            e.setPastImport(true);
            examsBean.add(e);

            temp = ce.get(ce.size() - i - 1);
            Exam e2 = new Exam();
            e2.setCourseExecution(temp);
            e2.setAsking(temp.getLecturer1().getName() + " " + temp.getLecturer1().getSurname());
            int randomNum2 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            if(randomNum2 == 1)
                e2.setLocation("PA");
            else if(randomNum2 == 2)
                e2.setLocation("PB");
            else
                e2.setLocation("P22");
            e2.setExamTerm(1);
            e2.setWritten(true);
            e2.setScheduledAt(winter.get(winter.size() - i*spacing - 1));
            e2.setPastImport(true);
            examsBean.add(e2);
        }
    }

    private void linkDatesAutum(LocalDate start, LocalDate end,  List<CourseExecution> ce) {
        List<Date> winter = getDatesBetweenWithoutWeekend(start,end);
        int numOfDaysInWinter = getDatesBetween(start,end).size();
        int spacing = (int) Math.floor(numOfDaysInWinter/ ce.size());
        for(int i = 0; i < ce.size(); i++ ){
            CourseExecution temp = ce.get(i);

            Exam e = new Exam();
            e.setCourseExecution(temp);
            e.setAsking(temp.getLecturer1().getName() + " " + temp.getLecturer1().getSurname());
            int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            if(randomNum == 1)
                e.setLocation("PA");
            else if(randomNum == 2)
                e.setLocation("PB");
            else
                e.setLocation("P22");
            e.setExamTerm(1);
            e.setWritten(true);
            e.setScheduledAt(winter.get(i*spacing));
            e.setPastImport(true);
            examsBean.add(e);
        }
    }

    private static List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    private static List<Date> getDatesBetweenWithoutWeekend(LocalDate startDate, LocalDate endDate) {
        List<Date> result = new ArrayList<>();
        for (LocalDate d : getDatesBetween(startDate, endDate)) {
            if (!d.getDayOfWeek().equals(DayOfWeek.SUNDAY) && !d.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                result.add(asDate(d));
            }
        }

        return result;
    }

    private static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
