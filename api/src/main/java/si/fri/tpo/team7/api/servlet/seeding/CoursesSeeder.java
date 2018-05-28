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

        c = new Course(); c.setName("Inteligentni sistemi"); c.setId(63266); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Umetno zaznavanje"); c.setId(63267); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Razvoj inteligentnih sistemov"); c.setId(63268); c.setEcts(6); coursesBean.add(c);

        c = new Course(); c.setName("Multimedijski sistemi"); c.setId(63270); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Računalniška grafika in tehnologija iger"); c.setId(63269); c.setEcts(6); coursesBean.add(c);
        c = new Course(); c.setName("Osnove oblikovanja"); c.setId(63271); c.setEcts(6); coursesBean.add(c);

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
