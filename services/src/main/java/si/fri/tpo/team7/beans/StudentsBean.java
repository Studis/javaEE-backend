package si.fri.tpo.team7.beans;

import si.fri.tpo.team7.entities.Student;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class StudentsBean {
    private Logger log = Logger.getLogger(StudentsBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @PostConstruct
    private void init()
    {
        log.info("StudentBean inicialized.");
    }

    @PreDestroy
    private void preDestroy()
    {
        log.info("StudentBean destroyed.");
    }

    public List<Student> getStudents() {
        return em.createNamedQuery("Student.getAll", Student.class).getResultList();
    }

    @Transactional
    public Student addStudent(Student s) {
        if(s != null){
            em.persist(s);
        }
        return s;
    }

}
