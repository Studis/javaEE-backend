package si.fri.tpo.team7.api.servlet.seeding;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import si.fri.tpo.team7.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.beans.exams.ExamsBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.exams.Exam;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExamSeeder extends Seeder {
    ExamsBean examsBean;
    CoursesBean coursesBean;

    public ExamSeeder(ExamsBean examsBean, CoursesBean coursesBean) {
        super("exams");
        this.examsBean = examsBean;
        this.coursesBean = coursesBean;
    }

    @Override
    protected void SeedContent() {
        Exam e;
        List<Course> courses = coursesBean.get();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.set(2018,Calendar.JANUARY,22); // Zimsko izpitno obdobje
        seedExamSchedule(calendar,courses, true);
        calendar.set(2018,Calendar.JULY,11); // Spomladansko izpitno obdobje
        seedExamSchedule(calendar,courses, false);
        calendar.set(2018,Calendar.AUGUST,20); // Jesensko izpitno obdobje
        seedExamSchedule(calendar,courses,false);
    }

    public void seedExamSchedule (Calendar calendar, List<Course> courses, boolean pastImport) {
        Exam e;
        Integer index = 0;
        for (Course course: courses) {
            do {
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.HOUR_OF_DAY, index % 6);
                calendar.add(calendar.SECOND, 43);
                e = new Exam();
                e.setPastImport(pastImport);
                e.setCourse(courses.get(index));
                e.setScheduledAt(calendar.getTime());
                e.setWritten(pastImport);
            }
            while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || examsBean.add(e) == null);
            index++;
        }
    }
}
