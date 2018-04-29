package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.services.beans.enrollments.StudyTypesBean;
import si.fri.tpo.team7.entities.enrollments.StudyType;

public class StudyTypesSeeder extends Seeder {

    private StudyTypesBean studyTypesBean;

    public StudyTypesSeeder(StudyTypesBean studyTypesBean) {
        super("study types");
        this.studyTypesBean = studyTypesBean;
    }

    @Override
    protected void SeedContent() {
        StudyType e;
        e = new StudyType(); e.setId(1); e.setName("Redni"); studyTypesBean.add(e);
        e = new StudyType(); e.setId(3); e.setName("Izredni"); studyTypesBean.add(e);
    }
}
