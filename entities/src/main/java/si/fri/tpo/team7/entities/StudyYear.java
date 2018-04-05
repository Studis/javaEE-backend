package si.fri.tpo.team7.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class StudyYear {
    @Id
    @Column(name = "year", length = 4)
    private int year;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="year")
    private Set<CourseExecution> courseExecutions;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="year")
    private Set<Curriculum> curriculums;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<CourseExecution> getCourseExecutions() { return courseExecutions; }

    @Override
    public String toString(){
        return year+"/"+(year+1);
    }
}
