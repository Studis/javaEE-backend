package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.services.beans.curriculum.*;
import si.fri.tpo.team7.services.beans.enrollments.*;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.services.beans.pojo.ResidencesBean;
import si.fri.tpo.team7.services.beans.users.AdministratorBean;
import si.fri.tpo.team7.services.beans.users.LecturersBean;
import si.fri.tpo.team7.services.beans.users.MunicipalitiesBean;
import si.fri.tpo.team7.services.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.curriculum.*;
import si.fri.tpo.team7.entities.enrollments.Enrollment;
import si.fri.tpo.team7.entities.enrollments.EnrollmentCourse;
import si.fri.tpo.team7.entities.users.Administrator;
import si.fri.tpo.team7.entities.users.Lecturer;
import si.fri.tpo.team7.entities.users.Residence;
import si.fri.tpo.team7.entities.users.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@WebServlet("/seed")
@ApplicationScoped
public class DatabaseSeeder extends HttpServlet{
    private Logger log = Logger.getLogger(DatabaseSeeder.class.getName());
    Random r = new Random();

    @Inject private StudentsBean studentsBean;
    @Inject private LecturersBean lecturersBean;
    @Inject private AdministratorBean administratorBean;
    @Inject private CoursesBean coursesBean;
    @Inject private ProgramsBean programsBean;
    @Inject private YearsBean yearsBean;
    @Inject private ModulesBean modulesBean;
    @Inject private EnrollmentTokensBean enrollmentTokensBean;
    @Inject private EnrollmentsBean enrollmentsBean;
    @Inject private EnrollmentCoursesBean enrollmentCoursesBean;
    @Inject private ResidencesBean residencesBean;
    @Inject private EnrollmentTypesBean enrollmentTypesBean;
    @Inject private StudyTypesBean studyTypesBean;
    @Inject private StudyFormsBean studyFormsBean;
    @Inject private MunicipalitiesBean municipalitiesBean;
    @Inject private StudyYearsBean studyYearsBean;
    @Inject private CourseExecutionsBean courseExecutionsBean;
    @Inject private CurriculumsBean curriculumsBean;
    @Inject private ExamsBean examsBean;

    Program uniProgram, vsProgram;
    private Lecturer ViljanMahnic, IgorKononenko, BorutRobic, BostjanSlivnik, BrankoSter, UrosLotric, GasperFijavz,
            TomazHovelja, DanijelSkocaj, PolonaOblak, ZoranBosnic, DejanLavbic, NezkaMramor, MatijaMarolt,
            MarkoRobnik, FrancSolina, NikolajZimic, MarkoBajec, PatricioBulic, PolonaLavbic, AleksandarJurisic,
            BojanOrel, DarjaPeljhan, JakaLindic, MatejaDrnovsek, PaulBorutKersevan, MatejKristan, LukaCehovin,
            NavrikaBovcon;

    String[] names = new String[]{
            "Franc", "Janez", "Ivan", "Anton", "Jožef", "Gašper", "Aleks", "Aljaž", "Bojan", "Ciril", "Daniel", "Dejan",
            "Marija", "Ana", "Maja", "Irena", "Mojca", "Dragica", "Eva", "Ines", "Kaja", "Lara", "Lidija", "Majda"};

    String[] surnames = new String[]{
            "Kozel", "Kovačič", "Mlakar", "Struna", "Žitnik", "Zupan", "Strnad", "Vlašič", "Jež", "Horvat", "Kokot", "Korošec",
            "Knavs", "Gosar", "Osterc", "Lapajne", "Žagar", "Ramovš", "Kotnik", "Ahačič", "Kolar", "Kašpar", "Furlan", "Babič"};
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        runSeed(writer);

        writer.println("Done");
    }

    protected void runSeed (PrintWriter writer) {
        int startYear = 2015;
        int endYear = 2018;

        new EnrollmentTypesSeeders(enrollmentTypesBean).Seed(writer);
        new StudyTypesSeeder(studyTypesBean).Seed(writer);
        new StudyFormsSeeder(studyFormsBean).Seed(writer);
        new MunicipalitiesSeeder(municipalitiesBean).Seed(writer);
        new CoursesSeeder(coursesBean).Seed(writer);
        new YearsSeeder(startYear, endYear, yearsBean, studyYearsBean).Seed(writer);
        new ExamSeeder(examsBean, coursesBean).Seed(writer);

        AddPrograms(writer);
        AddLecturers(writer);
        AddResidence();
        AddModulesAndCourses(writer, 2015);
        AddModulesAndCourses(writer, 2016);
        AddModulesAndCourses(writer, 2017);
        AddModulesAndCourses(writer, 2018);
        AddStudents(writer);
        AddAdmin(writer);
    }

    private void AddPrograms(PrintWriter writer){
        writer.print("Adding programs ... ");

        uniProgram = new Program();
        uniProgram.setId(1000475);
        uniProgram.setTitle("Računalništvo in informatika UNI");
        uniProgram.setEcts(180);
        programsBean.add(uniProgram);

        vsProgram = new Program();
        vsProgram.setId(1000477);
        vsProgram.setTitle("Računalništvo in informatika VS");
        vsProgram.setEcts(180);
        programsBean.add(vsProgram);

        writer.println("Done");
    }

    private void AddLecturers(PrintWriter writer){
        writer.print("Adding lecturers ... ");

        ViljanMahnic = AddLecturer("Viljan", "Mahnič");
        IgorKononenko = AddLecturer("Igor", "Kononenko");
        BorutRobic = AddLecturer("Borut", "Robič");
        BostjanSlivnik = AddLecturer("Boštjan", "Slivnik");
        BrankoSter = AddLecturer("Branko", "Šter");
        UrosLotric = AddLecturer("Uroš", "Lotrič");
        GasperFijavz = AddLecturer("Gašper", "Fijavž");
        TomazHovelja = AddLecturer("Tomaž", "Hovelja");
        DanijelSkocaj = AddLecturer("Danijel", "Skočaj");
        PolonaOblak = AddLecturer("Polona", "Oblak");
        ZoranBosnic = AddLecturer("Zoran", "Bosnić");
        DejanLavbic = AddLecturer("Dejan", "Lavbič");
        NezkaMramor  = AddLecturer("Nežka", "Mramor Kosta");
        MatijaMarolt = AddLecturer("Matija", "Marolt");
        MarkoRobnik  = AddLecturer("Marko", "Robnik Šikonja");
        FrancSolina = AddLecturer("Franc", "Solina");
        NikolajZimic = AddLecturer("Nikolaj", "Zimic");
        MarkoBajec = AddLecturer("Marko", "Bajec");
        PatricioBulic = AddLecturer("Patricio", "Bulić");
        PolonaLavbic = AddLecturer("Polona", "Lavbič");
        AleksandarJurisic = AddLecturer("Aleksandar", "Jurišić");
        BojanOrel = AddLecturer("Bojan", "Orel");
        DarjaPeljhan = AddLecturer("Darja", "Peljhan");
        JakaLindic = AddLecturer("Jaka", "Lindič");
        MatejaDrnovsek = AddLecturer("Mateja", "Drnovšek");
        PaulBorutKersevan = AddLecturer("Paul Borut", "Kerševan");
        MatejKristan = AddLecturer("Matej", "Kristan");
        LukaCehovin = AddLecturer("Luka", "Čehovin Zajc");
        NavrikaBovcon = AddLecturer("Navrika", "Bovcon");

        writer.println("Done");
    }

    private Lecturer AddLecturer(String name, String surname){
        Lecturer l = new Lecturer();
        l.setName(name);
        l.setSurname(surname);
        l.setUsername(name.toLowerCase()+"."+surname.replace(' ', '.').toLowerCase());
        lecturersBean.addLecturer(l);
        return l;
    }

    private void AddModulesAndCourses(PrintWriter writer, int year){
        writer.print("Adding modules and courses ... ");

        Year y = yearsBean.get(year);
        Module m;
        ObligatoryCourse c;
        ModuleCourse mc;

        Curriculum cur;
        cur = new Curriculum(); cur.setProgram(uniProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(1)); curriculumsBean.add(cur);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(ViljanMahnic); c.setCourse(coursesBean.get(63277)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(GasperFijavz); c.setCourse(coursesBean.get(63203)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(PaulBorutKersevan); c.setCourse(coursesBean.get(63205)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(NikolajZimic); c.setCourse(coursesBean.get(63204)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(PolonaOblak); c.setCourse(coursesBean.get(63202)); c.setWinter(true); courseExecutionsBean.add(c);

        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BostjanSlivnik); c.setCourse(coursesBean.get(63278)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BrankoSter); c.setCourse(coursesBean.get(63212)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(ZoranBosnic); c.setCourse(coursesBean.get(63209)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BojanOrel); c.setCourse(coursesBean.get(63207)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(DejanLavbic); c.setCourse(coursesBean.get(63215)); c.setWinter(false); courseExecutionsBean.add(c);

        cur = new Curriculum(); cur.setProgram(uniProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(2)); curriculumsBean.add(cur);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(PatricioBulic); c.setCourse(coursesBean.get(63218)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BorutRobic); c.setCourse(coursesBean.get(63283)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(IgorKononenko); c.setCourse(coursesBean.get(63279)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(AleksandarJurisic); c.setCourse(coursesBean.get(63213)); c.setWinter(true); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(MarkoBajec); c.setCourse(coursesBean.get(63208)); c.setWinter(true); courseExecutionsBean.add(c);

        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(UrosLotric); c.setCourse(coursesBean.get(63216)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BorutRobic); c.setCourse(coursesBean.get(63217)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(BorutRobic); c.setCourse(coursesBean.get(63280)); c.setWinter(false); courseExecutionsBean.add(c);

        cur = new Curriculum(); cur.setProgram(uniProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(3)); curriculumsBean.add(cur);
        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(ZoranBosnic); c.setCourse(coursesBean.get(63214)); c.setWinter(true); courseExecutionsBean.add(c);

        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(FrancSolina); c.setCourse(coursesBean.get(63281)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse();
        c.setCurriculum(cur);
        c.setLecturer3(JakaLindic); c.setLecturer2(DarjaPeljhan); c.setLecturer1(MatejaDrnovsek);
        c.setCourse(coursesBean.get(63248)); c.setWinter(false); courseExecutionsBean.add(c);

        List<Curriculum> curriculums = new ArrayList<>();
        curriculums.add(cur);

        GeneralOptionalCourse goc;
        goc = new GeneralOptionalCourse(); goc.setCourse(coursesBean.get(63281)); goc.setLecturer1(BorutRobic); goc.setCurriculums(curriculums); courseExecutionsBean.add(goc);

        ProfessionalOptionalCourse poc;
        poc = new ProfessionalOptionalCourse(); poc.setCourse(coursesBean.get(63281)); poc.setLecturer1(BorutRobic); poc.setCurriculums(curriculums); courseExecutionsBean.add(poc);

        m = new Module(); m.setName("Umetna inteligenca"); m.setCurriculum(cur); modulesBean.add(m);
        mc = new ModuleCourse(); mc.setLecturer1(IgorKononenko); mc.setLecturer2(MarkoRobnik); mc.setCourse(coursesBean.get(63266)); mc.setModule(m); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(MatejKristan); mc.setCourse(coursesBean.get(63267)); mc.setModule(m); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(DanijelSkocaj); mc.setModule(m); mc.setCourse(coursesBean.get(63268)); courseExecutionsBean.add(mc);

        m = new Module(); m.setName("Medijske tehnologije"); m.setCurriculum(cur); modulesBean.add(m);
        mc = new ModuleCourse(); mc.setLecturer1(LukaCehovin); mc.setModule(m); mc.setCourse(coursesBean.get(63270)); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(MatijaMarolt); mc.setModule(m); mc.setCourse(coursesBean.get(63269));  courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(NavrikaBovcon); mc.setModule(m); mc.setCourse(coursesBean.get(63271)); courseExecutionsBean.add(mc);

        m = new Module(); m.setName("Informacijski sistemi"); m.setCurriculum(cur); modulesBean.add(m);

        m = new Module(); m.setName("Algoritmi in sistemski programi"); m.setCurriculum(cur); modulesBean.add(m);

        m = new Module(); m.setName("Obvladovanje informatike"); m.setCurriculum(cur); modulesBean.add(m);

        m = new Module(); m.setName("Razvoj programske opreme"); m.setCurriculum(cur); modulesBean.add(m);

        m = new Module(); m.setName("Računalniška omrežja"); m.setCurriculum(cur); modulesBean.add(m);

        m = new Module(); m.setName("Računalniški sistemi"); m.setCurriculum(cur); modulesBean.add(m);



        writer.println("Done");
    }

    private void AddStudents(PrintWriter writer){
        writer.print("Adding students ... ");
        for(int i = 0; i < names.length; i++){
            Student student = new Student();
            student.setName(names[i]);
            student.setSurname(surnames[i]);
            student.setTemporary(AddResidence());
            student.setPermanent(AddResidence());
            student.setPhoneNumber(AddPhoneNumber());
            studentsBean.addStudent(student);

            /*EnrollmentToken token = new EnrollmentToken();
            token.setStudent(student);
            enrollmentTokensBean.add(token);

            Enrollment enrollment = new Enrollment();
            enrollment.setToken(token);*/

            /*switch(i%3){
                case 0:
                    enrollment.setStudyYear1(m1.getStudyYear());
                    enrollment.setStudyYear2(m2.getStudyYear());
                    enrollment.setType(enrollmentTypesBean.get(1));
                    break;
                case 1:
                    enrollment.setStudyYear1(m3.getStudyYear());
                    enrollment.setStudyYear2(m4.getStudyYear());
                    enrollment.setType(enrollmentTypesBean.get(3));
                    break;
                case 2:
                    enrollment.setStudyYear1(m5.getStudyYear());
                    enrollment.setStudyYear2(m6.getStudyYear());
                    enrollment.setType(enrollmentTypesBean.get(3));
                    break;
            }*/

            //enrollmentsBean.add(enrollment);

            /*switch(i%3){
                case 0:
                    Enroll(enrollment, m1);
                    Enroll(enrollment, m2);
                    break;
                case 1:
                    Enroll(enrollment, m3);
                    Enroll(enrollment, m4);
                    break;
                case 2:
                    Enroll(enrollment, m5);
                    Enroll(enrollment, m6);
                    Enroll(enrollment, i1);
                    Enroll(enrollment, i2);
                    break;
            }*/
        }
        writer.println("Done");
    }

    private void Enroll(Enrollment enrollment, Module module){
        Module m = modulesBean.get(module.getId());
        for(CourseExecution course: m.getCourses()) {
            EnrollmentCourse enrollmentCourse = new EnrollmentCourse();
            enrollmentCourse.setEnrollment(enrollment);
            enrollmentCourse.setCourseExecution(course);
            enrollmentCoursesBean.add(enrollmentCourse);
        }
    }

    private Module AddModule(String name, Curriculum curriculum){
        Module module = new Module();
        module.setName(name);
        module.setCurriculum(curriculum);
        modulesBean.add(module);
        return module;
    }

    private void AddAdmin(PrintWriter writer){
        writer.print("Adding admin ... ");

        Administrator a = new Administrator();
        a.setUsername("admin");
        a.setPassword("admin");
        administratorBean.addAdmin(a);
        writer.println("Done");
    }

    private Residence AddResidence(){
        String[] postCodes = new String[]{"4000", "1000", "2000"};
        String[] places = new String[]{"Kolodvorska cesta 10", "Večna pot 100", "Ulica Generala Maistra 10"};
        Residence r1 = new Residence();

        int rand = r.nextInt(postCodes.length);
        int municipality;
        if(rand == 0) {
            municipality = 52;
        } else if(rand == 1) {
            municipality = 61;
        } else {
            municipality = 70;
        }
        r1.setCountry("SI");
        r1.setMunicipality(municipalitiesBean.get(municipality));
        r1.setPlaceOfResidence(places[rand]);
        r1.setPostalNumber(postCodes[rand]);
        residencesBean.add(r1);
        return r1;
    }

    private String AddPhoneNumber() {
        String[] prefixes = new String[]{"031", "041", "070", "040"};
        String suffix = Integer.toString(r.nextInt(899999) + 100000);
        return prefixes[r.nextInt(prefixes.length)] + suffix;
    }
}
