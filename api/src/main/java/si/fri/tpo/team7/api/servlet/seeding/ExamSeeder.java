package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.entities.curriculum.CourseExecution;
import si.fri.tpo.team7.services.beans.curriculum.CourseExecutionsBean;
import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.validators.DateValidator;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        List<CourseExecution> courseExecutions = courseExecutionsBean.get();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.set(2018,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, true);
        calendar.set(2018,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions, false);
        calendar.set(2018,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courseExecutions,false);
    }

    public void seedExamSchedule (Calendar calendar, List<CourseExecution> courseExecutions, boolean pastImport) {
        Exam e;
        Integer index = 0;
        String[] locations = {"PA","P01","P02","P03","P04","P05","P06","P07","P08","P09","P10","P11","P12","P13","P14","P15","P16"};
        for (CourseExecution courseExecution: courseExecutions) {
            do {
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.HOUR_OF_DAY, index % 6);
                calendar.add(calendar.SECOND, 43);
                e = new Exam();
                e.setPastImport(pastImport);
                e.setCourseExecution(courseExecution);
                e.setScheduledAt(calendar.getTime());
                e.setWritten(DateValidator.isBefore(calendar.getTime().toInstant(), Instant.now()));
                e.setAsking(courseExecution.getLecturer1().getName() + " " + courseExecution.getLecturer1().getSurname());
                e.setLocation(locations[index%locations.length]);
            }
            while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || examsBean.add(e) == null);
            index++;
        }
    }
}
