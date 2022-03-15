package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Answer;
import vlad.model.Survey.Question;
import vlad.model.Survey.TypeOfQuestionEnum;
import vlad.repository.AnswerRepository;
import vlad.repository.QuestionRepository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionWithNuberOfAnswersDTO {

    private Long id;

    private String text;

    private TypeOfQuestionEnum typeOfQuestion;

    private long surveyId;

    private long numberOfAnswers;

    private float numFrom;
    private float numTo;

    public QuestionWithNuberOfAnswersDTO (Question q, AnswerRepository answerRepository){
        this.id = q.getId();
        this.text = q.getText();
        this.surveyId = q.getSurveyId();
        this.typeOfQuestion = q.getTypeOfQuestion();
        this.numberOfAnswers = answerRepository.findAllByQuestionId(q.getId()).size() ;
        if(q.getTypeOfQuestion().equals(TypeOfQuestionEnum.NUMERIC)){
            this.setNumFrom(q.getNumFrom());
            this.setNumTo(q.getNumTo());
        }
    }

}
