package si.fri.tpo.team7.services.beans.curriculum;

import si.fri.tpo.team7.services.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramsBean extends RegisterBean<Program> {
    public ProgramsBean() {
        super(Program.class);
    }
}
