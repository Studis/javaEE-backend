package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.beans.curriculum.StudyYearsBean;
import si.fri.tpo.team7.beans.curriculum.YearsBean;
import si.fri.tpo.team7.entities.curriculum.StudyYear;
import si.fri.tpo.team7.entities.curriculum.Year;

public class YearsSeeder extends Seeder {

    int start, end;
    YearsBean yearsBean;
    StudyYearsBean studyYearsBean;

    public YearsSeeder(int start, int end, YearsBean yearsBean, StudyYearsBean studyYearsBean) {
        super("years and study years");
        this.start = start;
        this.end = end;
        this.yearsBean = yearsBean;
        this.studyYearsBean = studyYearsBean;
    }

    @Override
    protected void SeedContent() {
        for(int yearNumber = start; yearNumber <= end; yearNumber++) {
            Year year = new Year();
            year.setId(yearNumber);
            yearsBean.add(year);
        }

        for(int i = 1; i <= 3; i++){
            StudyYear studyYear = new StudyYear();
            studyYear.setId(i);
            studyYearsBean.add(studyYear);
        }
    }
}
