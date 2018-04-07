package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.*;
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
    @JoinColumn(name="year_id")
    private Year year;

    @ManyToOne
    @JoinColumn(name="program")
    private Program program;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="semester")
    private Set<Module> modules;

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

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString(){
        return (number%2==0?"Zimski ":"Letni ")+year.toString();
    }
}
