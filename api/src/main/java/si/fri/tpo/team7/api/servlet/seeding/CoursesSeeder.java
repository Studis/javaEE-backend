package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.services.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.entities.curriculum.Course;

public class CoursesSeeder extends Seeder {
    CoursesBean coursesBean;

    public CoursesSeeder(CoursesBean coursesBean) {
        super("courses");
        this.coursesBean = coursesBean;
    }

    @Override
    protected void SeedContent() {
        Course c;

        //UNI
        // Obligatory courses
        c = new Course(); c.setName("Programiranje 1"); c.setId(63277); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Diskretne strukture"); c.setId(63203); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Fizika"); c.setId(63205); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove digitalnih vezij"); c.setId(63204); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove matematične analize"); c.setId(63202); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Programiranje 2"); c.setId(63278); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Arhitektura računalniških sistemov"); c.setId(63212); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Računalniške komunikacije"); c.setId(63209); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Linearna algebra"); c.setId(63207); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove informacijskih sistemov"); c.setId(63215); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Organizacija računalniških sistemov"); c.setId(63218); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Izračunljivost in računska zahtevnost"); c.setId(63283); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 1"); c.setId(63279); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Verjetnost in statistika"); c.setId(63213); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove podatkovnih baz"); c.setId(63208); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Teorija informacijskih sistemov"); c.setId(63216); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Operacijski sistemi"); c.setId(63217); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 2"); c.setId(63280); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Osnove umetne inteligence"); c.setId(63214); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Diplomski seminar"); c.setId(63281); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Ekonomika in podjetništvo"); c.setId(63248); c.setEcts(6); coursesBean.add(c);

        // Professional optional courses
        c = new Course(); c.setName("Matematično modeliranje"); c.setId(63219); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Principi programskih jezikov"); c.setId(63220); c.setEcts(6); coursesBean.add(c);

        // General optional courses
        c = new Course(); c.setName("Angleški jezik - nivo A"); c.setId(63222); c.setEcts(3); coursesBean.add(c);
        c = new Course(); c.setName("Angleški jezik - nivo B"); c.setId(63223); c.setEcts(3); coursesBean.add(c);
        c = new Course(); c.setName("Angleški jezik - nivo C"); c.setId(63224); c.setEcts(3); coursesBean.add(c);

        //Module courses

        //umetna inteligenca
        c = new Course(); c.setName("Inteligentni sistemi"); c.setId(63266); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Umetno zaznavanje"); c.setId(63267); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Razvoj inteligentnih sistemov"); c.setId(63268); c.setEcts(6); coursesBean.add(c);

        //medisjke tehnologije
        c = new Course(); c.setName("Multimedijski sistemi"); c.setId(63270); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Računalniška grafika in tehnologija iger"); c.setId(63269); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove oblikovanja"); c.setId(63271); c.setEcts(6); coursesBean.add(c);

        //informacijski sistemi
        c = new Course(); c.setName("Tehnologija upravljanja podatkov"); c.setId(63226); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Razvoj informacijskih sistemov"); c.setId(63252); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Planiranje in upravljanje informatike"); c.setId(63253); c.setEcts(6); coursesBean.add(c);

        //obvladovanje informatike
        c = new Course(); c.setName("Elektronsko poslovanje"); c.setId(63249); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Uvod v odkrivanje znanj iz podatkov"); c.setId(63251); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Organizacija in management"); c.setId(63250); c.setEcts(6); coursesBean.add(c);

        //razvoj programske opreme
        c = new Course(); c.setName("Postopki razvoja programske opreme"); c.setId(63254); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Spletno programiranje"); c.setId(63255); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Tehnologija programske opreme"); c.setId( 63256); c.setEcts(6); coursesBean.add(c);

        //raračunalniška omrežja
        c = new Course(); c.setName("Komunikacijski protokoli"); c.setId(63258); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Modeliranje računalniških omrežij"); c.setId(63257); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Brezžična in mobilna omrežja"); c.setId(63259); c.setEcts(6); coursesBean.add(c);

        //računalniški sistemi
        c = new Course(); c.setName("Zanesljivost in zmogljivost računalniških sistemov"); c.setId(63262); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Porazdeljeni sistemi"); c.setId(63261); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Digitalno načrtovanje"); c.setId(63260); c.setEcts(6); coursesBean.add(c);

        //algoritmi in sistemski programi
        c = new Course(); c.setName("Računska zahtevnost in hevristično programiranje"); c.setId(63263); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Sistemska programska oprema"); c.setId(63264); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Prevajalniki"); c.setId(63265); c.setEcts(6); coursesBean.add(c);



        //VSS
        c = new Course(); c.setName("Programiranje 2"); c.setId(63706); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Podatkovne baze"); c.setId(63707); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove verjetnosti in statistike"); c.setId(63710); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Računalniške komunikacije"); c.setId(63708); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Operacijski sistemi"); c.setId(63709); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Računalniška arhitektura"); c.setId(63703); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Uvod v računalništvo"); c.setId(63701); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Matematika"); c.setId(63704); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Programiranje 1"); c.setId(63702); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Diskretne strukture"); c.setId(63705); c.setEcts(6); coursesBean.add(c);
    }
}
