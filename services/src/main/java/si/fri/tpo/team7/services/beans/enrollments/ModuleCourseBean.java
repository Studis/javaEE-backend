package si.fri.tpo.team7.services.beans.enrollments;


import si.fri.tpo.team7.entities.curriculum.GeneralOptionalCourse;
import si.fri.tpo.team7.entities.curriculum.Module;
import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.entities.exams.Exam;
import si.fri.tpo.team7.services.beans.EntityBean;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;


@ApplicationScoped
public class ModuleCourseBean extends EntityBean<ModuleCourse> {
    public ModuleCourseBean() {
        super(ModuleCourse.class);
    }

    public List<ModuleCourse> getModulesForCurriculumId(Integer curriculumId) {
        Query q = em.createQuery("SELECT distinct(m) from ModuleCourse m where m.module.curriculum.id=:curriculumId");
        q.setParameter("curriculumId", curriculumId);
        return (List<ModuleCourse>) q.getResultList();
    }
}