package si.fri.tpo.team7.entities.curriculum;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue(value = "Obligatory")
public class ObligatoryCourse extends CourseExecution {

    @ManyToOne
    @JoinColumn(name="curriculum", referencedColumnName = "id", nullable=false)
    private Curriculum curriculum;
    @Override
    public Year getYear() {
        return curriculum.getYear();
    }
}
