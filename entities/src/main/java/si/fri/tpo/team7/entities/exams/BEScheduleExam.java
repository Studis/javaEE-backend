package si.fri.tpo.team7.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.eclipse.persistence.annotations.ExistenceChecking;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.curriculum.CourseExecution;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BEScheduleExam {
    private Integer courseExecution;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledAt;
}
