package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.Course;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoursesBean extends EntityBean<Course> {
    public CoursesBean() {
        super(Course.class);
    }

}
