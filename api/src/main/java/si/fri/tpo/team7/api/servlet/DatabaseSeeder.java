package si.fri.tpo.team7.api.servlet;

import si.fri.tpo.team7.beans.curriculum.*;
import si.fri.tpo.team7.beans.users.LecturersBean;
import si.fri.tpo.team7.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.curriculum.*;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.inject.Inject;
import javax.persistence.OneToMany;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static javax.swing.UIManager.put;

@WebServlet("/seed")
public class DatabaseSeeder extends HttpServlet{
    @Inject
    private StudentsBean studentsBean;
    @Inject
    private LecturersBean lecturersBean;
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

    Program uniProgram, vsProgram;
    private Lecturer ViljanMahnic, IgorKononenko, BorutRobic, BostjanSlivnik, BrankoSter, UrosLotric, GasperFijavz,
            TomazHovelja, DanijelSkocaj, PolonaOblak, ZoranBosnic, DejanLavbic, NezkaMramor, MatijaMarolt,
            MarkoRobnik, FrancSolina, NikolajZimic, MarkoBajec, PatricioBulic, PolonaLavbic, AleksandarJurisic,
            BojanOrel, DarjaPeljhan, JakaLindic, MatejaDrnovsek, PaulBorutKersevan;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        int startYear = 2015;
        int endYear = 2017;
        AddPrograms();
        AddYearsAndSemesters(startYear, endYear);
        AddLecturers();
        AddModulesAndCourses();

        writer.println("Done");
    }

    private void AddPrograms(PrintWriter writer){
        writer.print("Adding programs ... ");

        uniProgram = new Program();
        uniProgram.setCode(1000475);
        uniProgram.setTitle("Računalništvo in informatika UNI");
        uniProgram.setEcts(180);
        programsBean.add(uniProgram);

        vsProgram = new Program();
        vsProgram.setCode(1000477);
        vsProgram.setTitle("Računalništvo in informatika VS");
        vsProgram.setEcts(180);
        programsBean.add(vsProgram);

        writer.println("Done");
    }

    private void AddYearsAndSemesters(PrintWriter writer, int startYear, int endYear){
        writer.print("Adding years and semesters ... ");

        for(int yearNumber = startYear; yearNumber <= endYear; yearNumber++){
            Year year = new Year();
            year.setYear(yearNumber);
            yearsBean.addYear(year);
            Map<Integer, Semester> semesterMap = year.getSemesters();

            for (int semesterNumber = 1; semesterNumber <= 6; semesterNumber++) {
                Semester semester = new Semester();
                semester.setYear(year);
                semester.setNumber(semesterNumber);
                semester.setProgram(uniProgram);
                semestersBean.add(semester);
                semesterMap.put(semester.getId(), semester);
            }

            year.setSemesters(semesterMap);
            yearsBean.updateYear(year);
        }
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

    private void AddModulesAndCourses(PrintWriter writer){
        writer.print("Adding modules and courses ... ");

        Year y2015 = yearsBean.getYear(2015);
        Map<Integer, Semester> semesters = y2015.getSemesters();
        Module m;
        Course c;
        m = AddModule("1. semester obvezni", semesters.get(1), true);
        c = new Course(); c.setName("Programiranje 1"); c.setLecturer1(ViljanMahnic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Diskretne strukture"); c.setLecturer1(GasperFijavz); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Fizika"); c.setLecturer1(PaulBorutKersevan); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Osnove digitalnih vezij"); c.setLecturer1(NikolajZimic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Osnove matematične analize"); c.setLecturer1(PolonaOblak); c.setModule(m); coursesBean.add(c);

        m = AddModule("2. semester obvezni", semesters.get(2), true);
        c = new Course(); c.setName("Programiranje 2"); c.setLecturer1(BostjanSlivnik); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Arhitektura računalniških sistemov"); c.setLecturer1(BrankoSter); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Računalniške komunikacije"); c.setLecturer1(ZoranBosnic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Linearna algebra"); c.setLecturer1(BojanOrel); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Osnove informacijskih sistemov"); c.setLecturer1(DejanLavbic); c.setModule(m); coursesBean.add(c);

        m = AddModule("3. semester obvezni", semesters.get(3), true);
        c = new Course(); c.setName("Organizacija računalniških sistemov"); c.setLecturer1(PatricioBulic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Izračunljivost in računska zahtevnost"); c.setLecturer1(BorutRobic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 1"); c.setLecturer1(IgorKononenko); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Verjetnost in statistika"); c.setLecturer1(AleksandarJurisic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Osnove podatkovnih baz"); c.setLecturer1(MarkoBajec); c.setModule(m); coursesBean.add(c);

        m = AddModule("4. semester obvezni", semesters.get(4), true);
        c = new Course(); c.setName("Teorija informacijskih sistemov"); c.setLecturer1(UrosLotric); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Operacijski sistemi"); c.setLecturer1(BorutRobic); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 2"); c.setLecturer1(BorutRobic); c.setModule(m); coursesBean.add(c);

        m = AddModule("5. semester obvezni", semesters.get(5), true);
        c = new Course(); c.setName("Osnove umetne inteligence"); c.setLecturer1(ZoranBosnic); c.setModule(m); coursesBean.add(c);

        m = AddModule("6. semester obvezni", semesters.get(6), true);
        c = new Course(); c.setName("Diplomski seminar"); c.setLecturer1(FrancSolina); c.setModule(m); coursesBean.add(c);
        c = new Course(); c.setName("Ekonomika in podjetništvo");
        c.setLecturer1(JakaLindic); c.setLecturer1(DarjaPeljhan); c.setLecturer1(MatejaDrnovsek);
        c.setModule(m); coursesBean.add(c);

        writer.println("Done");
    }

    private Module AddModule(String name, Semester semester, Boolean obligatory){
        Module module = new Module();
        module.setName(name);
        module.setObligatory(obligatory);
        module.setSemester(semester);
        modulesBean.add(module);
        return module;
    }
}
