package si.fri.tpo.team7.entities.exams;


import lombok.Data;

import javax.persistence.Column;

@Data
public class BEExamResults {

    private Integer mark;


    private Integer score;


    private Boolean cancelEnrollment;
}
