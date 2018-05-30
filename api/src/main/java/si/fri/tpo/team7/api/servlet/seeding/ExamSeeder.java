package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.validators.DateValidator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExamSeeder extends Seeder {
    ExamsBean examsBean;
    CourseExecutionsBean courseExecutionsBean;

    public ExamSeeder(ExamsBean examsBean, CourseExecutionsBean courseExecutionsBean) {
        super("exams");
        this.examsBean = examsBean;
        this.courseExecutionsBean = courseExecutionsBean;
    }

    @Override
    protected void SeedContent() {
        Exam e;
        List<CourseExecution> courseExecutions = courseExecutionsBean.get()
                .stream()
                .filter(c -> c.getYear() != null && c.getYear().getId() == 2017)
                .collect(Collectors.toList());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.set(2018,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 1,true);
        calendar.set(2018,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 2,false);
        calendar.set(2018,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, false);
        calendar.set(2018,Calendar.AUGUST,21); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, false);
        calendar.set(2018,Calendar.OCTOBER,21); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, false);

        courseExecutions = courseExecutionsBean.get()
                .stream()
                .filter(c -> c.getYear() != null && c.getYear().getId() == 2016)
                .collect(Collectors.toList());

        calendar.set(2017,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 1,true);
        calendar.set(2017,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 2,true);
        calendar.set(2017,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, true);

        courseExecutions = courseExecutionsBean.get()
                .stream()
                .filter(c -> c.getYear() != null && c.getYear().getId() == 2015)
                .collect(Collectors.toList());

        calendar.set(2016,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 1,true);
        calendar.set(2016,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 2,true);
        calendar.set(2016,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, true);

        courseExecutions = courseExecutionsBean.get()
                .stream()
                .filter(c -> c.getYear() != null && c.getYear().getId() == 2014)
                .collect(Collectors.toList());

        calendar.set(2015,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 1,true);
        calendar.set(2015,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, 2,true);
        calendar.set(2015,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,3, true);
    }

    public ArrayList<Exam> seedExamSchedule (Calendar calendar, List<CourseExecution> courseExecutions, int examTerm, boolean pastImport) {
        Exam e;
        ArrayList<Exam> exams = new ArrayList<>();
        Integer index = 0;
        String[] locations = {"PA","P01","P02","P03","P04","P05","P06","P07","P08","P09","P10","P11","P12","P13","P14","P15","P16"};
        for (CourseExecution courseExecution: courseExecutions) {
            do {
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.HOUR_OF_DAY, index % 6);
                calendar.add(Calendar.SECOND, 43);
                e = new Exam();
                e.setPastImport(pastImport);
                e.setCourseExecution(courseExecution);
                e.setScheduledAt(calendar.getTime());
                e.setWritten(DateValidator.isBefore(calendar.getTime().toInstant(), Instant.now()));
                e.setAsking(courseExecution.getLecturer1().getName() + " " + courseExecution.getLecturer1().getSurname());
                e.setLocation(locations[index%locations.length]);
                e.setExamTerm(examTerm);

            }
            while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || examsBean.add(e) == null);
            exams.add(e);
            index++;
        }
        return exams;
    }
}
