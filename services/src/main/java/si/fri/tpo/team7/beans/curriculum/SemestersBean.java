package si.fri.tpo.team7.beans.curriculum;

import si.fri.tpo.team7.beans.Bean;
import si.fri.tpo.team7.entities.curriculum.Semester;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SemestersBean extends Bean<Semester> {
    public SemestersBean() {
        super(Semester.class);
    }
}
