package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourseExecutionsBean extends EntityBean<CourseExecution> {
    public CourseExecutionsBean() {
        super(CourseExecution.class);
    }
}
