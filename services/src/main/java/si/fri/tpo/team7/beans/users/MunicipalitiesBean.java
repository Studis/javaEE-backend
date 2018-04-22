package si.fri.tpo.team7.beans.users;

import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.users.Municipality;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MunicipalitiesBean extends RegisterBean<Municipality> {
    public MunicipalitiesBean() {
        super(Municipality.class);
    }
}
