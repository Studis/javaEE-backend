package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.RegisterBean;
import si.fri.tpo.team7.entities.curriculum.Year;
import si.fri.tpo.team7.entities.users.Lecturer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class YearsBean extends RegisterBean<Year>{

    public YearsBean() {
        super(Year.class);
    }
}
