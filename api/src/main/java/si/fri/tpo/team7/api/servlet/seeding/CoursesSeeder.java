package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.beans.curriculum.CoursesBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;

public class CoursesSeeder extends Seeder {
    CoursesBean coursesBean;

    public CoursesSeeder(CoursesBean coursesBean) {
        super("courses");
        this.coursesBean = coursesBean;
    }

    @Override
    protected void SeedContent() {
        Course c;
        c = new Course(); c.setName("Programiranje 1"); c.setId(63277); coursesBean.add(c);
        c = new Course(); c.setName("Diskretne strukture"); c.setId(63203); coursesBean.add(c);
        c = new Course(); c.setName("Fizika"); c.setId(63205); coursesBean.add(c);
        c = new Course(); c.setName("Osnove digitalnih vezij"); c.setId(63204); coursesBean.add(c);
        c = new Course(); c.setName("Osnove matematične analize"); c.setId(63202); coursesBean.add(c);

        c = new Course(); c.setName("Programiranje 2"); c.setId(63278); coursesBean.add(c);
        c = new Course(); c.setName("Arhitektura računalniških sistemov"); c.setId(63212); coursesBean.add(c);
        c = new Course(); c.setName("Računalniške komunikacije"); c.setId(63209); coursesBean.add(c);
        c = new Course(); c.setName("Linearna algebra"); c.setId(63207); coursesBean.add(c);
        c = new Course(); c.setName("Osnove informacijskih sistemov"); c.setId(63215); coursesBean.add(c);

        c = new Course(); c.setName("Organizacija računalniških sistemov"); c.setId(63218); coursesBean.add(c);
        c = new Course(); c.setName("Izračunljivost in računska zahtevnost"); c.setId(63283); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 1"); c.setId(63279); coursesBean.add(c);
        c = new Course(); c.setName("Verjetnost in statistika"); c.setId(63213); coursesBean.add(c);
        c = new Course(); c.setName("Osnove podatkovnih baz"); c.setId(63208); coursesBean.add(c);

        c = new Course(); c.setName("Teorija informacijskih sistemov"); c.setId(63216); coursesBean.add(c);
        c = new Course(); c.setName("Operacijski sistemi"); c.setId(63217); coursesBean.add(c);
        c = new Course(); c.setName("Algoritmi in podatkovne strukture 2"); c.setId(63280); coursesBean.add(c);

        c = new Course(); c.setName("Osnove umetne inteligence"); c.setId(63214); coursesBean.add(c);

        c = new Course(); c.setName("Diplomski seminar"); c.setId(63281); coursesBean.add(c);
        c = new Course(); c.setName("Ekonomika in podjetništvo"); c.setId(63248); coursesBean.add(c);

        c = new Course(); c.setName("Inteligentni sistemi"); c.setId(63266); coursesBean.add(c);
        c = new Course(); c.setName("Umetno zaznavanje"); c.setId(63267); coursesBean.add(c);
        c = new Course(); c.setName("Razvoj inteligentnih sistemov"); c.setId(63268); coursesBean.add(c);

        c = new Course(); c.setName("Multimedijski sistemi"); c.setId(63270); coursesBean.add(c);
        c = new Course(); c.setName("Računalniška grafika in tehnologija iger"); c.setId(63269); coursesBean.add(c);
        c = new Course(); c.setName("Osnove oblikovanja"); c.setId(63271); coursesBean.add(c);
    }
}
