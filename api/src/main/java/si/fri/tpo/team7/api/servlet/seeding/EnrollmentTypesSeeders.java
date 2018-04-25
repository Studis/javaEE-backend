package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.beans.enrollments.EnrollmentTypesBean;
import si.fri.tpo.team7.entities.enrollments.EnrollmentType;

public class EnrollmentTypesSeeders extends Seeder {

    private EnrollmentTypesBean enrollmentTypesBean;

    public EnrollmentTypesSeeders(EnrollmentTypesBean enrollmentTypesBean) {
        super("enrollment types");
        this.enrollmentTypesBean = enrollmentTypesBean;
    }

    @Override
    protected void SeedContent() {
        EnrollmentType e;
        e = new EnrollmentType(); e.setId(1); e.setName("Prvi vpis v letnik/dodatno leto"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(2); e.setName("Ponavljanje letnika"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(3); e.setName("Nadaljevanje letnika"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(4); e.setName("Podaljšanje statusa študenta"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(5); e.setName("Vpis po merilih za prehode v višji letnik"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(6); e.setName("Vpis v semester skupnega št. programa"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(7); e.setName("Vpis po merilih za prehode v isti letnik"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(98); e.setName("Vpis za zaključek"); enrollmentTypesBean.add(e);
    }
}
