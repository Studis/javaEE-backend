package si.fri.tpo.team7.entities.curriculum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "Obligatory")
public class ObligatoryCourse extends CourseExecution {

    @ManyToOne
    @JoinColumn(name="curriculum", referencedColumnName = "id", nullable=false)
    private Curriculum curriculum;

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
