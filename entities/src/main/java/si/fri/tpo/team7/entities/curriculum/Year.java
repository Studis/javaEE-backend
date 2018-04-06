package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.curriculum.Semester;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Year {
    @Id
    @Column(name = "year", length = 4)
    private int year;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="year")
    private Set<Semester> semesters;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Semester> getSemesters() { return semesters; }

    @Override
    public String toString(){
        return year+"/"+(year+1);
    }
}
