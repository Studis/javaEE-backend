package si.fri.tpo.team7.services.beans.users;

import si.fri.tpo.team7.services.beans.RegisterBean;
import si.fri.tpo.team7.entities.location.Municipality;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

@ApplicationScoped
public class MunicipalitiesBean extends RegisterBean<Municipality> {
    public MunicipalitiesBean() {
        super(Municipality.class);
    }

    public Municipality getMunicipalityByName(String name) {
        Query q = em.createNamedQuery("Municipality.getByName");
        q.setParameter("name", name);
        return (Municipality) q.getSingleResult();
    }
}
