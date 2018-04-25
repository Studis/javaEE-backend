package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.Year;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class YearsBean extends RegisterBean<Year>{

    public YearsBean() {
        super(Year.class);
    }
}
