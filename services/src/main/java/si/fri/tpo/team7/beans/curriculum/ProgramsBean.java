package si.fri.tpo.team7.beans.curriculum;

import org.postgresql.util.PGobject;
import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ProgramsBean extends Bean<Program> {
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
