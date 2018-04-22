package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.beans.enrollments.StudyFormsBean;
import si.fri.tpo.team7.entities.enrollments.StudyForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

public class StudyFormsSeeder extends Seeder {

    private StudyFormsBean studyFormsBean;

    public StudyFormsSeeder(StudyFormsBean studyFormsBean) {
        super("study forms");
        this.studyFormsBean = studyFormsBean;
    }

    @Override
    protected void SeedContent() {
        StudyForm e;
        e = new StudyForm(); e.setId(1); e.setName("Na lokaciji"); studyFormsBean.add(e);
        e = new StudyForm(); e.setId(2); e.setName("Na daljavo"); studyFormsBean.add(e);
        e = new StudyForm(); e.setId(3); e.setName("E-študij"); studyFormsBean.add(e);
    }
}
