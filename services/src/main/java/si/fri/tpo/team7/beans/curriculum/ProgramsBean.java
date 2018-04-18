package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramsBean extends RegisterBean<Program> {
    public ProgramsBean() {
        super(Program.class);
    }
}
