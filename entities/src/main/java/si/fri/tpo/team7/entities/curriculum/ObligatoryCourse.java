package si.fri.tpo.team7.entities.curriculum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Obligatory")
public class ObligatoryCourse extends Course {
}
