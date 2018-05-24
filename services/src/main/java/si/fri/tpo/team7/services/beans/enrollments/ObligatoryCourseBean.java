package si.fri.tpo.team7.services.beans.enrollments;


import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.services.beans.EntityBean;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;


@ApplicationScoped
public class ObligatoryCourseBean extends EntityBean<ObligatoryCourse> {
    public ObligatoryCourseBean() {
        super(ObligatoryCourse.class);
    }

    public List<ObligatoryCourse> getObligotoryCoursesForCurriculumId(Integer curriculumId) {
        Query q = em.createQuery("SELECT distinct(o) from ObligatoryCourse o where o.curriculum.id=:curriculumId");
        q.setParameter("curriculumId", curriculumId);
        return (List<ObligatoryCourse>) q.getResultList();
    }
}