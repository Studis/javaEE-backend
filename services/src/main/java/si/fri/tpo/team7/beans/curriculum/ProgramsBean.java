package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.curriculum.Program;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramsBean extends Bean<Program> {
    public ProgramsBean() {
        super(Program.class);
    }
}
