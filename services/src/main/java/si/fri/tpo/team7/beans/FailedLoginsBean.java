package si.fri.tpo.team7.beans;

import si.fri.tpo.team7.entities.FailedLogin;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class FailedLoginsBean extends Bean<FailedLogin> {
    public FailedLoginsBean() {
        super(FailedLogin.class);
    }

    public FailedLogin getByIp(String ip){
        Object obj = em.createQuery("SELECT f FROM FailedLogin f WHERE f.ip LIKE :ip")
                .setParameter("ip", ip)
                .getSingleResult();

        FailedLogin obj1 = (FailedLogin) obj;
        em.refresh(obj1);
        return obj1;
    }

    @Transactional
    public boolean allowedToLogin(String ip){
        try {
            FailedLogin fl = getByIp(ip);
            if(ChronoUnit.HOURS.between(fl.getTimestamp(), Instant.now()) > 24){
                return true;
            }
            return fl.getTrials() < 3;
        }
        catch (Exception e){
            return true;
        }
    }

    @Transactional
    public void logFailedLogin(String ip){
        FailedLogin fl;
        try {
            fl = getByIp(ip);
            fl.setTrials(fl.getTrials()+1);
            update(fl.getId(), fl);
        }
        catch(Exception e){
            fl = new FailedLogin();
            fl.setIp(ip);
            fl.setTrials(1);
            fl.setTimestamp(Instant.now());
            add(fl);
        }
    }

    @Transactional
    public void logSuccessfulLogin(String ip){
        try {
            FailedLogin fl = getByIp(ip);
            remove(fl.getId());
        }
        catch(Exception e){

        }
    }
}
