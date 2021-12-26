package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Question;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionForAttempt {

    private Long id;

    private String text;

    private long surveyId;

    private List<AnswerForAttempt> answerForAttemptList;


    public QuestionForAttempt(Question question){
        this.id =question.getId();
        this.text = question.getText();
        this.surveyId = question.getSurveyId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public List<AnswerForAttempt> getAnswerForAttemptList() {
        return answerForAttemptList;
    }

    public void setAnswerForAttemptList(List<AnswerForAttempt> answerForAttemptList) {
        this.answerForAttemptList = answerForAttemptList;
    }
}
