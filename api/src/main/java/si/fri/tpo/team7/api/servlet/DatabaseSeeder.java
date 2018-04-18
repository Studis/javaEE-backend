package si.fri.tpo.team7.api.servlet;

import si.fri.tpo.team7.beans.curriculum.*;
import si.fri.tpo.team7.beans.enrollments.*;
import si.fri.tpo.team7.beans.users.AdministratorBean;
import si.fri.tpo.team7.beans.users.LecturersBean;
import si.fri.tpo.team7.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.curriculum.*;
import si.fri.tpo.team7.entities.enrollments.*;
import si.fri.tpo.team7.entities.users.Administrator;
import si.fri.tpo.team7.entities.users.Lecturer;
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
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/seed")
@ApplicationScoped
public class DatabaseSeeder extends HttpServlet{
    private Logger log = Logger.getLogger(DatabaseSeeder.class.getName());

    @Inject
    private StudentsBean studentsBean;
    @Inject
    private LecturersBean lecturersBean;
    @Inject
    private AdministratorBean administratorBean;
    @Inject
    private CoursesBean coursesBean;
    @Inject
    private ProgramsBean programsBean;
    @Inject
    private YearsBean yearsBean;
    @Inject
    private SemestersBean semestersBean;
    @Inject
    private ModulesBean modulesBean;
    @Inject
    private EnrollmentTokensBean enrollmentTokensBean;
    @Inject
    private EnrollmentsBean enrollmentsBean;
    @Inject
    private EnrollmentTypesBean enrollmentTypesBean;
    @Inject
    private EnrollmentCoursesBean enrollmentCoursesBean;
    @Inject
    private StudyFormsBean studyFormsBean;
    @Inject
    private StudyTypesBean studyTypesBean;

    Program uniProgram, vsProgram;
    private Lecturer ViljanMahnic, IgorKononenko, BorutRobic, BostjanSlivnik, BrankoSter, UrosLotric, GasperFijavz,
            TomazHovelja, DanijelSkocaj, PolonaOblak, ZoranBosnic, DejanLavbic, NezkaMramor, MatijaMarolt,
            MarkoRobnik, FrancSolina, NikolajZimic, MarkoBajec, PatricioBulic, PolonaLavbic, AleksandarJurisic,
            BojanOrel, DarjaPeljhan, JakaLindic, MatejaDrnovsek, PaulBorutKersevan, MatejKristan, LukaCehovin,
            NavrikaBovcon;

    Module m1, m2, m3, m4, m5, m6, i1, i2, i3, i4;

    String[] names = new String[]{
            "Franc", "Janez", "Ivan", "Anton", "Jožef", "Gašper", "Aleks", "Aljaž", "Bojan", "Ciril", "Daniel", "Dejan",
            "Marija", "Ana", "Maja", "Irena", "Mojca", "Dragica", "Eva", "Ines", "Kaja", "Lara", "Lidija", "Majda"};

    String[] surnames = new String[]{
            "Kozel", "Kovačič", "Mlakar", "Struna", "Žitnik", "Zupan", "Strnad", "Vlašič", "Jež", "Horvat", "Kokot", "Korošec",
            "Knavs", "Gosar", "Osterc", "Lapajne", "Žagar", "Ramovš", "Kotnik", "Ahačič", "Kolar", "Kašpar", "Furlan", "Babič"};

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        log.info("Seeder started running");
        PrintWriter writer = new PrintWriter(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        });

        runSeed(writer);

        log.info("Seeder finnished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        runSeed(writer);

        writer.println("Done");
    }

    protected void runSeed (PrintWriter writer) {
        int startYear = 2015;
        int endYear = 2018;
        AddEnrolmentTypes(writer);
        AddStudyTypes(writer);
        AddStudyForms(writer);
        AddPrograms(writer);
        AddYearsAndSemesters(writer, startYear, endYear);
        AddLecturers(writer);
        AddModulesAndCourses(writer, 2015);
        AddModulesAndCourses(writer, 2016);
        AddModulesAndCourses(writer, 2017);
        AddModulesAndCourses(writer, 2018);
        //AddStudents(writer);
        AddAdmin(writer);
    }

    private void AddPrograms(PrintWriter writer){
        log.info("Adding programs ... ");

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

        log.info("Done");
    }

    private void AddYearsAndSemesters(PrintWriter writer, int startYear, int endYear){
        log.info("Adding years and semesters ... ");

        for(int yearNumber = startYear; yearNumber <= endYear; yearNumber++){
            Year year = new Year();
            year.setId(yearNumber);
            yearsBean.add(year);
            Map<Integer, StudyYear> semesterMap = year.getSemesters();

            for (int semesterNumber = 1; semesterNumber <= 6; semesterNumber++) {
                StudyYear studyYear = new StudyYear();
                studyYear.setYear(year);
                studyYear.setNumber(semesterNumber);
                studyYear.setProgram(uniProgram);
                semestersBean.add(studyYear);
                semesterMap.put(studyYear.getId(), studyYear);
            }

            year.setSemesters(semesterMap);
            yearsBean.update(year.getId(), year);
        }
        log.info("Done");
    }

    private void AddLecturers(PrintWriter writer){
        log.info("Adding lecturers ... ");

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

        log.info("Done");
    }

    private Lecturer AddLecturer(String name, String surname){
        Lecturer l = new Lecturer();
        l.setName(name);
        l.setSurname(surname);
        l.setUsername(name.toLowerCase()+"."+surname.replace(' ', '.').toLowerCase());
        lecturersBean.addLecturer(l);
        return l;
    }

    private void AddEnrolmentTypes(PrintWriter writer){
        log.info("Adding enrollment types... ");

        EnrollmentType e;
        e = new EnrollmentType(); e.setId(1); e.setName("Prvi vpis v letnik/dodatno leto"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(2); e.setName("Ponavljanje letnika"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(3); e.setName("Nadaljevanje letnika"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(4); e.setName("Podaljšanje statusa študenta"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(5); e.setName("Vpis po merilih za prehode v višji letnik"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(6); e.setName("Vpis v semester skupnega št. programa"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(7); e.setName("Vpis po merilih za prehode v isti letnik"); enrollmentTypesBean.add(e);
        e = new EnrollmentType(); e.setId(98); e.setName("Vpis za zaključek"); enrollmentTypesBean.add(e);

        log.info("Done");
    }

    private void AddStudyTypes(PrintWriter writer){
        log.info("Adding study types... ");

        StudyType e;
        e = new StudyType(); e.setId(1); e.setName("Redni"); studyTypesBean.add(e);
        e = new StudyType(); e.setId(3); e.setName("Izredni"); studyTypesBean.add(e);

        log.info("Done");
    }

    private void AddStudyForms(PrintWriter writer){
        log.info("Adding study types... ");

        StudyForm e;
        e = new StudyForm(); e.setId(1); e.setName("Na lokaciji"); studyFormsBean.add(e);
        e = new StudyForm(); e.setId(2); e.setName("Na daljavo"); studyFormsBean.add(e);
        e = new StudyForm(); e.setId(3); e.setName("E-študij"); studyFormsBean.add(e);

        log.info("Done");
    }

    private void AddModulesAndCourses(PrintWriter writer, int year){
        log.info("Adding modules and courses ... ");

        Year y = yearsBean.get(year);
        Map<Integer, StudyYear> semesters = y.getSemesters();
        ArrayList<Integer> keys = new ArrayList<>(semesters.keySet());
        Module m;
        ObligatoryCourse c;
        m1 = AddModule("1. semester obvezni", semesters.get(keys.get(0)), true);
        c = new ObligatoryCourse(); c.setName("Programiranje 1"); c.setLecturer1(ViljanMahnic); /*c.setModule(m1);*/ c.setCode(63277); coursesBean.add(c);
        /*c = new ObligatoryCourse(); c.setName("Diskretne strukture"); c.setLecturer1(GasperFijavz); c.setModule(m1); c.setCode(63203); coursesBean.add(c);
        c = new ObligatoryCourse(); c.setName("Fizika"); c.setLecturer1(PaulBorutKersevan); c.setModule(m1); c.setCode(63205); coursesBean.add(c);
        c = new ObligatoryCourse(); c.setName("Osnove digitalnih vezij"); c.setLecturer1(NikolajZimic); c.setModule(m1); c.setCode(63204); coursesBean.add(c);
        c = new ObligatoryCourse(); c.setName("Osnove matematične analize"); c.setLecturer1(PolonaOblak); c.setModule(m1); c.setCode(63202); coursesBean.add(c);

        m2 = AddModule("2. semester obvezni", semesters.get(keys.get(1)), true);
        c = new Course(); c.setName("Programiranje 2"); c.setLecturer1(BostjanSlivnik); c.setModule(m2); c.setCode(63278); coursesBean.add(c);
        c = new Course(); c.setName("Arhitektura računalniških sistemov"); c.setLecturer1(BrankoSter); c.setModule(m2); c.setCode(63212); coursesBean.add(c);
        c = new Course(); c.setName("Računalniške komunikacije"); c.setLecturer1(ZoranBosnic); c.setModule(m2); c.setCode(63209); coursesBean.add(c);
        c = new Course(); c.setName("Linearna algebra"); c.setLecturer1(BojanOrel); c.setModule(m2); c.setCode(63207); coursesBean.add(c);
        c = new Course(); c.setName("Osnove informacijskih sistemov"); c.setLecturer1(DejanLavbic); c.setModule(m2); c.setCode(63215); coursesBean.add(c);

        m3 = AddModule("3. semester obvezni", semesters.get(keys.get(2)), true);
        c = new Course(); c.setName("Organizacija računalniških sistemov"); c.setLecturer1(PatricioBulic); c.setModule(m3); c.setCode(63218); coursesBean.add(c);
        c = new Course(); c.setName("Izračunljivost in računska zahtevnost"); c.setLecturer1(BorutRobic); c.setModule(m3); c.setCode(63283); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 1"); c.setLecturer1(IgorKononenko); c.setModule(m3); c.setCode(63279); coursesBean.add(c);
        c = new Course(); c.setName("Verjetnost in statistika"); c.setLecturer1(AleksandarJurisic); c.setModule(m3); c.setCode(63213); coursesBean.add(c);
        c = new Course(); c.setName("Osnove podatkovnih baz"); c.setLecturer1(MarkoBajec); c.setModule(m3); c.setCode(63208); coursesBean.add(c);

        m4 = AddModule("4. semester obvezni", semesters.get(keys.get(3)), true);
        c = new Course(); c.setName("Teorija informacijskih sistemov"); c.setLecturer1(UrosLotric); c.setModule(m4); c.setCode(63216); coursesBean.add(c);
        c = new Course(); c.setName("Operacijski sistemi"); c.setLecturer1(BorutRobic); c.setModule(m4); c.setCode(63217); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 2"); c.setLecturer1(BorutRobic); c.setModule(m4); c.setCode(63280); coursesBean.add(c);

        m5 = AddModule("5. semester obvezni", semesters.get(keys.get(4)), true);
        c = new Course(); c.setName("Osnove umetne inteligence"); c.setLecturer1(ZoranBosnic); c.setModule(m5); c.setCode(63214); coursesBean.add(c);

        m6 = AddModule("6. semester obvezni", semesters.get(keys.get(5)), true);
        c = new Course(); c.setName("Diplomski seminar"); c.setLecturer1(FrancSolina); c.setModule(m6); c.setCode(63281); coursesBean.add(c);
        c = new Course(); c.setName("Ekonomika in podjetništvo");
        c.setLecturer3(JakaLindic); c.setLecturer2(DarjaPeljhan); c.setLecturer1(MatejaDrnovsek);
        c.setCode(63248);
        c.setModule(m6); coursesBean.add(c);

        i1 = AddModule("Umetna inteligenca 1", semesters.get(keys.get(4)), false);
        c = new Course(); c.setName("Inteligentni sistemi"); c.setLecturer1(IgorKononenko); c.setLecturer2(MarkoRobnik); c.setModule(i1); c.setCode(63266); coursesBean.add(c);
        c = new Course(); c.setName("Umetno zaznavanje"); c.setLecturer1(MatejKristan); c.setCode(63267); c.setModule(i1); coursesBean.add(c);

        i2 = AddModule("Umetna inteligenca 2", semesters.get(keys.get(5)), false);
        c = new Course(); c.setName("Razvoj inteligentnih sistemov"); c.setLecturer1(DanijelSkocaj); c.setModule(i2); c.setCode(63268); coursesBean.add(c);
*/
        ModuleCourse mc;
        i3 = AddModule("Medijske tehnologije 1", semesters.get(keys.get(4)), false);
        mc = new ModuleCourse(); mc.setName("Multimedijski sistemi"); mc.setLecturer1(LukaCehovin); mc.setModule(i3); mc.setCode(63270); coursesBean.add(mc);
        mc = new ModuleCourse(); mc.setName("Računalniška grafika in tehnologija iger");
        mc.setLecturer1(MatijaMarolt); mc.setCode(63269); mc.setModule(i3); coursesBean.add(mc);

        i4 = AddModule("Medijske tehnologije 2", semesters.get(keys.get(5)), false);
        mc = new ModuleCourse(); mc.setName("Osnove oblikovanja"); mc.setLecturer1(NavrikaBovcon); mc.setModule(i4); mc.setCode(63271); coursesBean.add(mc);

        log.info("Done");
    }

    private void AddStudents(PrintWriter writer){
        log.info("Adding students ... ");
        for(int i = 0; i < names.length; i++){
            Student student = new Student();
            student.setName(names[i]);
            student.setSurname(surnames[i]);
            studentsBean.addStudent(student);

            EnrollmentToken token = new EnrollmentToken();
            token.setStudent(student);
            enrollmentTokensBean.add(token);

            Enrollment enrollment = new Enrollment();
            enrollment.setToken(token);


            switch(i%3){
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
            }

            enrollmentsBean.add(enrollment);

            switch(i%3){
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
            }
        }
        log.info("Done");
    }

    private void Enroll(Enrollment enrollment, Module module){
        Module m = modulesBean.get(module.getId());
        for(Course course: m.getCourses()) {
            EnrollmentCourse enrollmentCourse = new EnrollmentCourse();
            enrollmentCourse.setEnrollment(enrollment);
            enrollmentCourse.setCourse(course);
            enrollmentCoursesBean.add(enrollmentCourse);
        }
    }

    private Module AddModule(String name, StudyYear studyYear, Boolean obligatory){
        Module module = new Module();
        module.setName(name);
        module.setObligatory(obligatory);
        module.setStudyYear(studyYear);
        modulesBean.add(module);
        return module;
    }

    private void AddAdmin(PrintWriter writer){
        log.info("Adding admin ... ");

        Administrator a = new Administrator();
        a.setUsername("admin");
        a.setPassword("admin");
        administratorBean.addAdmin(a);
    }
}
