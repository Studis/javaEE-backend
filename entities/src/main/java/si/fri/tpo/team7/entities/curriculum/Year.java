package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.curriculum.Semester;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Year {
    @Id
    @Column(name = "year", length = 4)
    private int year;

    //@OneToMany(cascade=CascadeType.ALL, mappedBy="year", fetch = FetchType.EAGER)
    //@OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name="year_id")
    //@MapKey
    //private Map<Integer, Semester> semesters;

    public void setSemesters(Map<Integer, Semester> semesters) {
        this.semesters = semesters;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "year")
    @MapKey
    private Map<Integer, Semester> semesters;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<Integer, Semester> getSemesters() { return semesters; }

    @Override
    public String toString(){
        return year+"/"+(year+1);
    }
}
