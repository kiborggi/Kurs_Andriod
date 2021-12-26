package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Answer;
import vlad.repository.AnswerRepository;
import vlad.repository.AnswerTypeValueRepository;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerWithNumberOfTypesDTO {

    private long id;
    private long questionId;
    private String text;
    private long numberOfTypes;

    public  AnswerWithNumberOfTypesDTO  (Answer answer, AnswerTypeValueRepository answerTypeValueRepository){
        this.id = answer.getId();
        this.questionId = answer.getQuestionId();
        this.text = answer.getText();
        this.numberOfTypes = answerTypeValueRepository.findAllByAnswerId(answer.getId()).size();
    }

}
