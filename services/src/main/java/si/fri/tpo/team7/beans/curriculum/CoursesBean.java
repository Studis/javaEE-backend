package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.beans.users.StudentsBean;
import si.fri.tpo.team7.entities.curriculum.Course;
import si.fri.tpo.team7.entities.users.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CoursesBean extends Bean<Course> {
    public CoursesBean() {
        super(Course.class);
    }
}
