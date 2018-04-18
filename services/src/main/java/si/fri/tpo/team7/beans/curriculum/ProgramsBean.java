package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramsBean extends EntityBean<Program> {
    public ProgramsBean() {
        super(Program.class);
    }

    public Program getByCode(int code){
        Object obj = em.createQuery("SELECT p FROM Program p WHERE p.code = :programCode")
            .setParameter("programCode", code)
            .getSingleResult();
        return (Program)obj;
    }
}
