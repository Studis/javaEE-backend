package si.fri.tpo.team7.services.beans.curriculum;

import si.fri.tpo.team7.services.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.Module;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModulesBean extends EntityBean<Module> {
    public ModulesBean() {
        super(Module.class);
    }
}
