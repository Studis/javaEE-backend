package si.fri.tpo.team7.api.servlet.endpoints.enrollment;


import lombok.Data;
import si.fri.tpo.team7.entities.curriculum.*;

import java.util.List;

@Data
public class BEECurriculum {
    private List<Module> modules;
    private List<GeneralOptionalCourse> generalOptionalCourses;
    private List<ProfessionalOptionalCourse> professionalOptionalCourses;
    private List<ObligatoryCourse> obligatoryCourses;
    Curriculum curriculum;
}
