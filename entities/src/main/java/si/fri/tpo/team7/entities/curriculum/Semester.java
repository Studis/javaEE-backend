package si.fri.tpo.team7.entities.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Semester extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 4)
    private int id;

    @Column(name="number")
    private int number;

    @ManyToOne
    @JoinColumn(name="year", referencedColumnName = "year", nullable=false)
    private Year year;

    @ManyToOne
    @JoinColumn(name="program")
    private Program program;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semester")
    private List<Module> modules;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString(){
        return ((number+1)/2)+". "+(number%2==0?"Zimski ":"Letni ")+year.toString();
    }
}
