package si.fri.tpo.team7.services.beans.curriculum;

import si.fri.tpo.team7.entities.curriculum.Curriculum;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CourseExecutionsBean extends EntityBean<CourseExecution> {
    public CourseExecutionsBean() {
        super(CourseExecution.class);
    }

    public List<ObligatoryCourse> getObligatoryCourses(Curriculum curriculum){
        Query q = em.createQuery("SELECT c, o FROM CourseExecution c INNER JOIN ObligatoryCourse o ON (o.id = c.id)");
        List resultList = q.getResultList();
        List<ObligatoryCourse> obligatoryCourses = new ArrayList<>();
        for(int i = 0; i < resultList.size(); i++) {
            ObligatoryCourse o = (ObligatoryCourse) ((Object[])resultList.get(i))[0];
            if (o.getCurriculum().getId() == curriculum.getId()) obligatoryCourses.add(o);
        }

         return obligatoryCourses;

    }
}
