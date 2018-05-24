package si.fri.tpo.team7.api.servlet.endpoints.enrollment;


import si.fri.tpo.team7.api.servlet.annotations.Secured;
import si.fri.tpo.team7.entities.curriculum.GeneralOptionalCourse;
import si.fri.tpo.team7.entities.curriculum.ModuleCourse;
import si.fri.tpo.team7.entities.curriculum.ObligatoryCourse;
import si.fri.tpo.team7.entities.curriculum.ProfessionalOptionalCourse;
import si.fri.tpo.team7.entities.enrollments.BEEnrollmentCourses;
import si.fri.tpo.team7.entities.enums.Role;
import si.fri.tpo.team7.entities.exams.BEEnrollmentCourse;
import si.fri.tpo.team7.entities.exams.BEEnrollmentExam;
import si.fri.tpo.team7.services.beans.enrollments.*;
import si.fri.tpo.team7.services.beans.exams.ExamEnrollmentBean;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

public class EnrollmentCoursesEndPoint {

    @Inject
    ModuleCourseBean moduleCourseBean;

    @Inject
    ObligatoryCourseBean obligatoryCourseBean;

    @Inject
    ProfessionalOptionalCourseBean professionalOptionalCourseBean;

    @Inject
    GeneralOptionCourseBean generalOptionCourseBean;

    // Get all courses (general, option, professional) for study year
    @GET
    @Secured({Role.STUDENT,Role.ADMIN, Role.LECTURER, Role.CLERK})
    @Path("{curriculumId}")
    public Response getEnrollments(@PathParam("curriculumId") Integer curriculumId) {
        return Response.ok(getEnrollmentCourses(curriculumId)).build();
    }

    public BEEnrollmentCourses getEnrollmentCourses(Integer curriculumId) {
        BEEnrollmentCourses beEnrollmentCourse = new BEEnrollmentCourses();
        beEnrollmentCourse.setGeneralOptionalCourseList(getGeneralOptionalCourseForCurriculumId(curriculumId));
        beEnrollmentCourse.setModuleCourseList(getModulesForCurriculumId(curriculumId));
        beEnrollmentCourse.setObligatoryCourseList(getObligatoryCoursesForCurriculumId(curriculumId));
        beEnrollmentCourse.setProfessionalOptionalCourseList(getProffesionalOptionalCoursesForCurriculumId(curriculumId));
        return beEnrollmentCourse;
    }

    public List<ModuleCourse> getModulesForCurriculumId(Integer id) {
        return moduleCourseBean.getModulesForCurriculumId(id);
    }

    public List<ObligatoryCourse> getObligatoryCoursesForCurriculumId(Integer id) {
        return obligatoryCourseBean.getObligotoryCoursesForCurriculumId(id);
    }

    public List<ProfessionalOptionalCourse> getProffesionalOptionalCoursesForCurriculumId(Integer id) {
//        for(ProfessionalOptionalCourse professionalOptionalCourse: professionalOptionalCourseBean.get()) {
//        }
    }
    public List<GeneralOptionalCourse> getGeneralOptionalCourseForCurriculumId(Integer id) {

    }
}
