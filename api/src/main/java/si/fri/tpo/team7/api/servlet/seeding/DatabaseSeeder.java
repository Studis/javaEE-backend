package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.entities.enrollments.*;
import si.fri.tpo.team7.entities.enums.Status;
import si.fri.tpo.team7.entities.users.Clerk;
import si.fri.tpo.team7.services.beans.curriculum.*;
import si.fri.tpo.team7.services.beans.enrollments.*;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;
import si.fri.tpo.team7.services.beans.exams.ExamsBean;
import si.fri.tpo.team7.services.beans.pojo.ResidencesBean;
import si.fri.tpo.team7.services.beans.users.*;
import si.fri.tpo.team7.entities.curriculum.*;
import si.fri.tpo.team7.entities.users.Administrator;
import si.fri.tpo.team7.entities.users.Lecturer;
import si.fri.tpo.team7.entities.location.Residence;
import si.fri.tpo.team7.entities.users.Student;
import si.fri.tpo.team7.services.beans.validators.DateValidator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    @Inject private ExamEnrollmentBean examEnrollmentBean;
    @Inject private ClerksBean clerksBean;

    Program uniProgram, vsProgram;
    private Lecturer ViljanMahnic, IgorKononenko, BorutRobic, BostjanSlivnik, BrankoSter, UrosLotric, GasperFijavz,
            TomazHovelja, DanijelSkocaj, PolonaOblak, ZoranBosnic, DejanLavbic, NezkaMramor, MatijaMarolt,
            MarkoRobnik, FrancSolina, NikolajZimic, MarkoBajec, PatricioBulic, PolonaLavbic, AleksandarJurisic,
            BojanOrel, DarjaPeljhan, JakaLindic, MatejaDrnovsek, PaulBorutKersevan, MatejKristan, LukaCehovin,
            NavrikaBovcon, NinaBostic, AndrejBauer, TomazDobravec, MatjazKukar, MojcaCiglaric, PeterPeer, IgorSkraba, RomanDrnovsek, JanezDemsar, SandiKlavžar;

    private Clerk DanicaKotnik;

    String[] names = new String[]{"Franc", "Janez", "Ivan", "Anton", "Marko", "Andrej", "Jožef", "Jože", "Marjan", "Peter", "Luka", "Matej", "Milan", "Tomaž", "Branko", "Aleš", "Bojan", "Robert", "Rok", "Boštjan", "Stanislav", "Matjaž", "Gregor", "Miha", "Martin", "David", "Igor", "Boris", "Dejan", "Dušan", "Jan", "Alojz", "Žiga", "Nejc", "Jure", "Uroš", "Blaž", "Mitja", "Simon", "Žan", "Matic", "Klemen", "Darko", "Drago", "Jernej", "Primož", "Aleksander", "Gašper", "Miran", "Anže", "Štefan", "Roman", "Tadej", "Denis", "Aljaž", "Jaka", "Jakob", "Vladimir", "Srečko", "Nik", "Damjan", "Slavko", "Borut", "Janko", "Matija", "Mirko", "Miroslav", "Tilen", "Zoran", "Alen", "Danijel", "Domen", "Stanko", "Filip", "Mihael", "Goran", "Vid", "Alojzij", "Sašo", "Matevž", "Iztok", "Marijan", "Jurij", "Leon", "Urban", "Vinko", "Andraž", "Tim", "Mark", "Viktor", "Rudolf", "Zvonko", "Zdravko", "Benjamin", "Dragan", "Samo", "Danilo", "Pavel", "Erik", "Rajko", "Gorazd", "Maks", "Edvard", "Zlatko", "Bogdan", "Sandi", "Kristjan", "Gal", "Lovro", "Sebastjan", "Emil", "Franci", "Vojko", "Ludvik", "Josip", "Patrik", "Silvo", "Maj", "Timotej", "Anej", "Ciril", "Željko", "Damir", "Aljoša", "Damijan", "Dominik", "Albin", "Daniel", "Božidar", "Miloš", "Lan", "Frančišek", "Niko", "Nikola", "Silvester", "Leopold", "Viljem", "Stojan", "Tine", "Tomislav", "Saša", "Mario", "Davorin", "Aleks", "Aleksandar", "Karel", "Valentin", "Grega", "Vincenc", "Kristijan", "Mladen", "Vlado", "Franjo", "Davor", "Zdenko", "Marcel", "Ladislav", "Bogomir", "Sebastijan", "Jasmin", "Rene", "Ivo", "Elvis", "Bor", "Oskar", "Enej", "Karl", "Jani", "Nenad", "Stjepan", "Edin", "Rado", "Maksimiljan", "Ernest", "Valter", "Ervin", "Tian", "Izidor", "Nikolaj", "Nino", "Petar", "Sergej", "Avgust", "Metod", "Senad", "Teo", "Val", "Renato", "Radovan", "Ignac", "Mirsad", "Vito", "Bruno", "Adolf", "Slobodan", "Samir", "Bernard", "Alex", "Rudi", "Joško",
            "Marija", "Ana", "Maja", "Irena", "Mojca", "Mateja", "Nina", "Nataša", "Barbara", "Andreja", "Jožica", "Petra", "Jožefa", "Katja", "Anja", "Eva", "Sonja", "Tatjana", "Katarina", "Milena", "Tanja", "Sara", "Alenka", "Tina", "Ivana", "Vesna", "Martina", "Majda", "Frančiška", "Urška", "Nika", "Špela", "Terezija", "Tjaša", "Helena", "Anica", "Dragica", "Nada", "Kristina", "Darja", "Simona", "Danica", "Olga", "Marjeta", "Zdenka", "Suzana", "Angela", "Antonija", "Lidija", "Vida", "Neža", "Marta", "Ivanka", "Lara", "Sabina", "Janja", "Ema", "Veronika", "Silva", "Ljudmila", "Darinka", "Karmen", "Alojzija", "Maša", "Aleksandra", "Anita", "Stanislava", "Brigita", "Štefanija", "Metka", "Jana", "Monika", "Zala", "Cvetka", "Kaja", "Klara", "Lana", "Lucija", "Elizabeta", "Natalija", "Lea", "Nevenka", "Jasmina", "Slavica", "Marjana", "Renata", "Branka", "Tamara", "Saša", "Pavla", "Klavdija", "Vera", "Bernarda", "Manca", "Danijela", "Bojana", "Erika", "Julija", "Jasna", "Romana", "Hana", "Teja", "Rozalija", "Mira", "Polona", "Valentina", "Jelka", "Laura", "Mirjana", "Sandra", "Ajda", "Tadeja", "Valerija", "Sanja", "Maruša", "Nuša", "Ines", "Živa", "Patricija", "Mihaela", "Breda", "Ida", "Ksenija", "Karolina", "Gabrijela", "Neja", "Pia", "Vanja", "Albina", "Viktorija", "Julijana", "Vlasta", "Marjetka", "Magdalena", "Melita", "Marina", "Zoja", "Matilda", "Alja", "Ljubica", "Gordana", "Amalija", "Taja", "Marinka", "Zofija", "Nadja", "Cecilija", "Marica", "Polonca", "Ela", "Karin", "Urša", "Emilija", "Tea", "Nastja", "Mia", "Brina", "Damjana", "Tinkara", "Larisa", "Milka", "Doroteja", "Justina", "Jerneja", "Gaja", "Milica", "Marijana", "Vita", "Nives", "Jelena", "Lina", "Štefka", "Tia", "Rebeka", "Žana", "Dušanka", "Slavka", "Iva", "Andrejka", "Stanka", "Marjanca", "Lilijana", "Mirjam", "Irma", "Ana", "Zlatka", "Miroslava", "Iris", "Zvonka", "Jolanda", "Daša", "Ula", "Ivica", "Blanka", "Anamarija", "Erna", "Liljana", "Meta", "Alma", "Zora"};

    String[] surnames = new String[]{"Župančič", "Švejk", "Horvat", "Kovačič", "Čuš", "Krajnc", "Zupančič", "Dobravec", "Zupančič", "Kovač", "Kovčar", "Kos", "Vidmar", "Golob", "Turk", "Kralj", "Božič", "Korošec", "Bizjak", "Zupan", "Hribar", "Kotnik", "Kavčič", "Rozman", "Kastelic", "Oblak", "Petek", "Žagar", "Hočevar", "Kolar", "Košir", "Koren", "Klemenčič", "Zajc", "Knez", "Medved", "Zupanc", "Petrič", "Pirc", "Hrovat", "Pavlič", "Kuhar", "Lah", "Uršič", "Tomažič", "Zorko", "Sever", "Erjavec", "Babič", "Jereb", "Jerman", "Majcen", "Pušnik", "Kranjc", "Breznik", "Rupnik", "Lesjak", "Perko", "Dolenc", "Pečnik", "Močnik", "Furlan", "Pavlin", "Vidic", "Logar", "Kovačević", "Jenko", "Ribič", "Tomšič", "Žnidaršič", "Janežič", "Marolt", "Maček", "Jelen", "Pintar", "Blatnik", "Černe", "Petrović", "Gregorič", "Mihelič", "Dolinar", "Kokalj", "Lešnik", "Zadravec", "Fras", "Bezjak", "Cerar", "Hren", "Leban", "Čeh", "Jug", "Rus", "Vidovič", "Kocjančič", "Kobal", "Bogataj", "Primožič", "Kolenc", "Lavrič", "Kolarič", "Lazar", "Kodrič", "Nemec", "Mrak", "Kosi", "Hodžić", "Debeljak", "Ivančič", "Žižek", "Tavčar", "Žibert", "Jovanović", "Miklavčič", "Krivec", "Jarc", "Vovk", "Marković", "Vodopivec", "Zver", "Hribernik", "Likar", "Kramberger", "Toplak", "Gorenc", "Skok", "Jazbec", "Leskovar", "Stopar", "Eržen", "Meglič", "Železnik", "Sitar", "Simonič", "Šinkovec", "Blažič", "Petrovič", "Demšar", "Ilić", "Ramšak", "Javornik", "Jamnik", "Popović", "Kočevar", "Nikolić", "Hozjan", "Filipič", "Bregar", "Gorjup", "Čuk", "Volk", "Pintarič", "Bukovec", "Podgoršek", "Sušnik", "Kokol", "Koželj", "Rutar", "Rajh", "Kramar", "Godec", "Gajšek", "Resnik", "Mohorič", "Mavrič", "Rožman", "Šmid", "Pogačnik", "Gomboc", "Bergant", "Hafner", "Lebar", "Kumer", "Rožič", "Povše", "Zemljič", "Savić", "Bajc", "Mlinar", "Ambrožič", "Bevc", "Zakrajšek", "Cvetko", "Gashi", "Kristan", "Tratnik", "Kalan", "Markovič", "Pogačar", "Pavlović", "Zorman", "Mlinarič", "Jerič", "Kaučič", "Zalokar", "Babić", "Humar", "Trček", "Založnik", "Begić", "Štrukelj", "Gorišek", "Škof", "Šuštar","Čižmar"};


    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
//        log.info("Seeder started running");
//        DateValidator dateValidator = new DateValidator();
//        Instant today = Instant.now();
//        Instant tomorrow = Instant.now().plus(1, ChronoUnit.DAYS);
//        log.info("Shoud be true: " + dateValidator.isAfter(tomorrow,today));
//        log.info("Shoud be -1: " + dateValidator.durationBetweenDatesInDays(tomorrow,today));
//        log.info("Shoud be 1: " + dateValidator.durationBetweenDatesInDays(today,tomorrow));
//        log.info("Shoud be 22.5.2018: " + dateValidator.subtractDays(today,1));
//        log.info("Shoud be 24.5.2018: " + dateValidator.addDays(today,1));
//        log.info("Shoud be 21.5.2018: "  + dateValidator.getLatestEnrollDismentDate(today));
//        log.info("Shoud be 23.5.2018: "  + dateValidator.trunateToDays(today));


        PrintWriter writer = new PrintWriter(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                log.info(new String(b, off, len));
            }

        });
//
        runSeed(writer);
//
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

        new EnrollmentTypesSeeders(enrollmentTypesBean).Seed(writer);
        new StudyTypesSeeder(studyTypesBean).Seed(writer);
        new StudyFormsSeeder(studyFormsBean).Seed(writer);
        new MunicipalitiesSeeder(municipalitiesBean).Seed(writer);
        new CoursesSeeder(coursesBean).Seed(writer);
        new YearsSeeder(startYear, endYear, yearsBean, studyYearsBean).Seed(writer);


        AddPrograms(writer);
        AddLecturers(writer);
        AddClerks(writer);
        AddResidence();
        AddModulesAndCourses(writer, 2015);
        AddModulesAndCourses(writer, 2016);
        AddModulesAndCourses(writer, 2017);
        AddModulesAndCourses(writer, 2018);
        AddStudents(writer);
        AddAdmin(writer);

       new ExamSeeder(examsBean, courseExecutionsBean).Seed(writer);
       new ExamEnrollmentsSeeder(examsBean,examEnrollmentBean,enrollmentCoursesBean,studentsBean).Seed(writer);
    }

    private void AddPrograms(PrintWriter writer){
        writer.print("Adding programs ... ");

        uniProgram = new Program();
        uniProgram.setId(1000468);
        uniProgram.setTitle("Računalništvo in informatika UNI");
        uniProgram.setEcts(180);
        programsBean.add(uniProgram);

        vsProgram = new Program();
        vsProgram.setId(1000470);
        vsProgram.setTitle("Računalništvo in informatika VS");
        vsProgram.setEcts(180);
        programsBean.add(vsProgram);

        writer.println("Done");
    }

    private void AddLecturers(PrintWriter writer){
        writer.print("Adding lecturers ... ");

        ViljanMahnic = AddLecturer("Viljan", "Mahnič", 1);
        IgorKononenko = AddLecturer("Igor", "Kononenko", 2);
        BorutRobic = AddLecturer("Borut", "Robič", 3);
        BostjanSlivnik = AddLecturer("Boštjan", "Slivnik", 4);
        BrankoSter = AddLecturer("Branko", "Šter", 5);
        UrosLotric = AddLecturer("Uroš", "Lotrič", 6);
        GasperFijavz = AddLecturer("Gašper", "Fijavž", 7);
        TomazHovelja = AddLecturer("Tomaž", "Hovelja", 42);
        DanijelSkocaj = AddLecturer("Danijel", "Skočaj", 42);
        PolonaOblak = AddLecturer("Polona", "Oblak", 42);
        ZoranBosnic = AddLecturer("Zoran", "Bosnić", 42);
        DejanLavbic = AddLecturer("Dejan", "Lavbič", 42);
        NezkaMramor  = AddLecturer("Nežka", "Mramor Kosta", 42);
        MatijaMarolt = AddLecturer("Matija", "Marolt", 42);
        MarkoRobnik  = AddLecturer("Marko", "Robnik Šikonja", 42);
        FrancSolina = AddLecturer("Franc", "Solina", 42);
        NikolajZimic = AddLecturer("Nikolaj", "Zimic", 42);
        MarkoBajec = AddLecturer("Marko", "Bajec", 42);
        PatricioBulic = AddLecturer("Patricio", "Bulić", 42);
        PolonaLavbic = AddLecturer("Polona", "Lavbič", 42);
        AleksandarJurisic = AddLecturer("Aleksandar", "Jurišić", 42);
        BojanOrel = AddLecturer("Bojan", "Orel", 42);
        DarjaPeljhan = AddLecturer("Darja", "Peljhan", 42);
        JakaLindic = AddLecturer("Jaka", "Lindič", 42);
        MatejaDrnovsek = AddLecturer("Mateja", "Drnovšek", 42);
        PaulBorutKersevan = AddLecturer("Paul Borut", "Kerševan", 42);
        MatejKristan = AddLecturer("Matej", "Kristan", 42);
        LukaCehovin = AddLecturer("Luka", "Čehovin Zajc", 42);
        NavrikaBovcon = AddLecturer("Navrika", "Bovcon", 42);
        NinaBostic = AddLecturer("Nina", "Bostič Bishop", 42);
        AndrejBauer = AddLecturer("Andrej", "Bauer", 42);

        //VSS
        TomazDobravec = AddLecturer("Tomaž", "Dobravec", 123);
        MatjazKukar = AddLecturer("Matjaž", "Kukar", 123);
        //jurisicjeze
        MojcaCiglaric = AddLecturer("Mojca", "Ciglarič", 123);
        PeterPeer = AddLecturer("Peter", "Peer", 123);
        IgorSkraba = AddLecturer("Igor", "Škraba", 123);
        //skocajjeze
        RomanDrnovsek = AddLecturer("Roman", "Drnovšek", 123);
        JanezDemsar = AddLecturer("Janez", "Demsar", 123);
        SandiKlavžar = AddLecturer("Sandi", "Klavžar", 123);

        writer.println("Done");
    }

    private void AddClerks(PrintWriter writer) {
        writer.print("Adding Clerks ... ");

        DanicaKotnik = AddClerk("Danica", "Kotnik");
        writer.println("Done");

    }

    private Lecturer AddLecturer(String name, String surname, int code){
        Lecturer l = new Lecturer();
        l.setName(name);
        l.setSurname(surname);
        l.setCode(code);
        l.setUsername(name.toLowerCase()+"."+surname.replace(' ', '.').toLowerCase());
        lecturersBean.addLecturer(l);
        return l;
    }

    private Clerk AddClerk(String name, String surname){
        Clerk l = new Clerk();
        l.setName(name);
        l.setSurname(surname);
        l.setUsername(name.toLowerCase()+"."+surname.replace(' ', '.').toLowerCase());
        clerksBean.addClerk(l);
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
        addObligatory(cur, ViljanMahnic, 63277, true);
        addObligatory(cur, GasperFijavz, 63203, true);
        addObligatory(cur, PaulBorutKersevan, 63205, true);
        addObligatory(cur, NikolajZimic, 63204, true);
        addObligatory(cur, PolonaOblak, 63202, true);

        addObligatory(cur, BostjanSlivnik, 63278, false);
        addObligatory(cur, BrankoSter, 63212, false);
        addObligatory(cur, ZoranBosnic, 63209, false);
        addObligatory(cur, BojanOrel, 63207, false);
        addObligatory(cur, DejanLavbic, 63215, false);

        cur = new Curriculum(); cur.setProgram(vsProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(1)); curriculumsBean.add(cur);
        addObligatory(cur, IgorSkraba, 63703, true);
        addObligatory(cur, DanijelSkocaj, 63701, true);
        addObligatory(cur, RomanDrnovsek, 63704, true);
        addObligatory(cur, JanezDemsar, 63702, true);
        addObligatory(cur, SandiKlavžar, 63705, true);

        addObligatory(cur, TomazDobravec, 63706, false);
        addObligatory(cur, MatjazKukar, 63707, false);
        addObligatory(cur, AleksandarJurisic, 63710, false);
        addObligatory(cur, MojcaCiglaric, 63708, false);
        addObligatory(cur, PeterPeer, 63709, false);

        cur = new Curriculum(); cur.setProgram(uniProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(2)); curriculumsBean.add(cur);
        Curriculum cur2 = cur;
        addObligatory(cur, PatricioBulic, 63218, true);
        addObligatory(cur, BorutRobic, 63283, true);
        addObligatory(cur, IgorKononenko, 63279, true);
        addObligatory(cur, AleksandarJurisic, 63213, true);
        addObligatory(cur, MarkoBajec, 63208, true);

        addObligatory(cur, UrosLotric, 63216, false);
        addObligatory(cur, BorutRobic, 63217, false);
        addObligatory(cur, BorutRobic, 63280, false);

        cur = new Curriculum(); cur.setProgram(uniProgram); cur.setYear(y); cur.setStudyYear(studyYearsBean.get(3)); curriculumsBean.add(cur);
        addObligatory(cur, ZoranBosnic, 63214, true);

        c = new ObligatoryCourse(); c.setCurriculum(cur); c.setLecturer1(FrancSolina); c.setCourse(coursesBean.get(63281)); c.setWinter(false); courseExecutionsBean.add(c);
        c = new ObligatoryCourse();
        c.setCurriculum(cur);
        c.setLecturer3(JakaLindic); c.setLecturer2(DarjaPeljhan); c.setLecturer1(MatejaDrnovsek);
        c.setCourse(coursesBean.get(63248)); c.setWinter(false); courseExecutionsBean.add(c);

        List<Curriculum> curriculums = new ArrayList<>();
        curriculums.add(cur);
        curriculums.add(cur2);

        List<GeneralOptionalCourse> gocs = new ArrayList<>();
        GeneralOptionalCourse goc;
        goc = new GeneralOptionalCourse(); goc.setCourse(coursesBean.get(63222)); goc.setLecturer1(NinaBostic); goc.setCurriculums(curriculums); goc.setWinter(false); courseExecutionsBean.add(goc);
        gocs.add(goc);
        goc = new GeneralOptionalCourse(); goc.setCourse(coursesBean.get(63223)); goc.setLecturer1(NinaBostic); goc.setCurriculums(curriculums); goc.setWinter(true); courseExecutionsBean.add(goc);
        gocs.add(goc);
        goc = new GeneralOptionalCourse(); goc.setCourse(coursesBean.get(63224)); goc.setLecturer1(NinaBostic); goc.setCurriculums(curriculums); goc.setWinter(false); courseExecutionsBean.add(goc);
        gocs.add(goc);

        List<ProfessionalOptionalCourse> pocs = new ArrayList<>();
        ProfessionalOptionalCourse poc;
        poc = new ProfessionalOptionalCourse(); poc.setCourse(coursesBean.get(63219)); poc.setLecturer1(NezkaMramor); poc.setWinter(false); courseExecutionsBean.add(poc);
        pocs.add(poc);
        poc = new ProfessionalOptionalCourse(); poc.setCourse(coursesBean.get(63220)); poc.setLecturer1(AndrejBauer); poc.setWinter(false); courseExecutionsBean.add(poc);
        pocs.add(poc);

        for(Curriculum scur : curriculums){
            scur.setProfessionalOptionalCourses(pocs);
            scur.setGeneralOptionalCourses(gocs);
            curriculumsBean.update(scur.getId(), scur);
        }

        m = AddModule("Umetna inteligenca", cur);
        mc = new ModuleCourse(); mc.setLecturer1(IgorKononenko); mc.setLecturer2(MarkoRobnik); mc.setCourse(coursesBean.get(63266)); mc.setModule(m); mc.setWinter(true); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(MatejKristan); mc.setCourse(coursesBean.get(63267)); mc.setModule(m); mc.setWinter(true); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(DanijelSkocaj); mc.setCourse(coursesBean.get(63268)); mc.setModule(m); mc.setWinter(false); courseExecutionsBean.add(mc);

        m = AddModule("Medijske tehnologije", cur);
        mc = new ModuleCourse(); mc.setLecturer1(LukaCehovin); mc.setModule(m); mc.setCourse(coursesBean.get(63270)); mc.setWinter(true); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(MatijaMarolt); mc.setModule(m); mc.setCourse(coursesBean.get(63269)); mc.setWinter(true); courseExecutionsBean.add(mc);
        mc = new ModuleCourse(); mc.setLecturer1(NavrikaBovcon); mc.setModule(m); mc.setCourse(coursesBean.get(63271)); mc.setWinter(false); courseExecutionsBean.add(mc);

        m = AddModule("Informacijski sistemi", cur);

        m = AddModule("Algoritmi in sistemski programi", cur);

        m = AddModule("Obvladovanje informatike", cur);

        m = AddModule("Razvoj programske opreme", cur);

        m = AddModule("Računalniška omrežja", cur);

        m = AddModule("Računalniški sistemi", cur);

        writer.println("Done");
    }

    public void addObligatory(Curriculum cur, Lecturer lecturer, int courseId, boolean winter){
        ObligatoryCourse c = new ObligatoryCourse();
        c.setCurriculum(cur);
        c.setLecturer1(lecturer);
        c.setCourse(coursesBean.get(courseId));
        c.setWinter(winter);
        courseExecutionsBean.add(c);
    }

    private void AddStudents(PrintWriter writer){
        writer.print("Adding students ... ");
        Integer reduceSeeder = 25;
        for(int i = 0; i < surnames.length/reduceSeeder; i++){
            Student student = new Student();
            student.setName(names[i]);
            student.setSurname(surnames[i]);
            student.setTemporary(AddResidence());
            student.setPermanent(AddResidence());
            student.setPhoneNumber(AddPhoneNumber());
            student.setNationality("Slovenija");
            student.setPlaceOfBirth(new String[]{"Ljubljana", "Izola", "Jesenice", "Kranj", "Novo mesto", "Maribor"}[r.nextInt(6)]);
            Date date = null;
            try {
                String dateString = (r.nextInt(28)+1)+"/"+(r.nextInt(12)+1)+"/"+(r.nextInt(50)+1950);
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                student.setDateOfBirth(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            studentsBean.addStudent(student);


            switch(i%3) {
                case 0:
                    EnrollInYear(student, 2015, 1);
                    EnrollInYear(student, 2016, 2);
                    EnrollInYear(student, 2017, 3);
                    break;
                case 1:
                    EnrollInYear(student, 2016, 1);
                    EnrollInYear(student, 2017, 2);
                    EnrollInYear(student, 2018, 3);
                    break;
                case 2:
                    EnrollInYear(student, 2017, 1);
                    EnrollInYear(student, 2018, 2);

                    EnrollmentToken token = new EnrollmentToken();
                    token.setStudent(student);
                    token.setStudyYear(studyYearsBean.get(3));
                    token.setStudyForm(studyFormsBean.get(1));
                    token.setStudyType(studyTypesBean.get(1));
                    token.setStatus(Status.OPEN);
                    token.setProgram(uniProgram);
                    token.setEnrollmentType(enrollmentTypesBean.get(5));

                    enrollmentTokensBean.add(token);
                    break;
            }
        }
        writer.println("Done");
    }

    private void EnrollInYear(Student student, int year, int studyYear){
        Curriculum c = curriculumsBean.get(uniProgram, yearsBean.get(year), studyYearsBean.get(studyYear));

        EnrollmentToken token = new EnrollmentToken();
        token.setStudent(student);
        token.setStudyYear(studyYearsBean.get(studyYear));
        token.setStudyForm(studyFormsBean.get(1));
        token.setStudyType(studyTypesBean.get(1));
        token.setStatus(Status.COMPLETED);
        token.setProgram(uniProgram);
        switch(studyYear){
            case 1:
                token.setEnrollmentType(enrollmentTypesBean.get(1));
                break;
            case 2:
            case 3:
                token.setEnrollmentType(enrollmentTypesBean.get(5));
                break;
        }

        enrollmentTokensBean.add(token);

        Enrollment enrollment = new Enrollment();
        enrollment.setToken(token);
        enrollment.setCurriculum(c);

        switch(studyYear){
            case 1:
                enrollment.setType(enrollmentTypesBean.get(1));
                enrollment.setStudyType(studyTypesBean.get(1));
                enrollment.setStudyForm(studyFormsBean.get(1));
                break;
            case 2:
            case 3:
                enrollment.setCurriculum(c);
                enrollment.setType(enrollmentTypesBean.get(5));
                enrollment.setStudyType(studyTypesBean.get(1));

//                if (student.getId() == 1) {
//                    enrollment.setStudyType(studyTypesBean.get(3));
//                }

                enrollment.setStudyForm(studyFormsBean.get(1));
                break;
        }

        enrollmentsBean.add(enrollment);
        switch(studyYear){
            case 1:
                Enroll(enrollment, c.getObligatoryCourses());
                break;
            case 2:
                Enroll(enrollment, c.getObligatoryCourses());
                Enroll(enrollment, c.getGeneralOptionalCourses());
                Enroll(enrollment, c.getProfessionalOptionalCourses());
                break;
            case 3:
                Enroll(enrollment, c.getObligatoryCourses());
                Enroll(enrollment, c.getGeneralOptionalCourses());
                Enroll(enrollment, c.getProfessionalOptionalCourses());
                for(Module m : c.getModules()){
                    Enroll(enrollment, m.getCourses());
                }
                break;
        }
    }

    private void Enroll(Enrollment enrollment, List<? extends CourseExecution> courses){
        for(CourseExecution course: courses) {
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
        String[] places = new String[]{"Kolodvorska cesta", "Večna pot", "Ulica Generala Maistra"};
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
        r1.setPlaceOfResidence(places[rand]+" "+r.nextInt(50));
        r1.setPostalNumber(postCodes[rand]);
        residencesBean.add(r1);
        return r1;
    }

    private String AddPhoneNumber() {
        String[] prefixes = new String[]{"031", "041", "070", "040"};
        String suffix = Integer.toString(r.nextInt(899999) + 100000);
        return prefixes[r.nextInt(prefixes.length)] + suffix;
    }

    private void addSquad(){

    }
}
