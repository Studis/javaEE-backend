package si.fri.tpo.team7.services.beans.curriculum;

import si.fri.tpo.team7.services.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoursesBean extends RegisterBean<Course> {
    public CoursesBean() {
        super(Course.class);
    }

}
