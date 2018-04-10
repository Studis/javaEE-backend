package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.curriculum.Module;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModulesBean extends Bean<Module> {
    public ModulesBean() {
        super(Module.class);
    }
}
