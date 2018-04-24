package si.fri.tpo.team7.beans.pojo;

import si.fri.tpo.team7.beans.EntityBean;
import si.fri.tpo.team7.entities.users.Residence;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResidencesBean extends EntityBean<Residence> {
    public ResidencesBean() {
        super(Residence.class);
    }
}
