package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.curriculum.Curriculum;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CurriculumsBean extends EntityBean<Curriculum>{
    public CurriculumsBean() {
        super(Curriculum.class);
    }
}
