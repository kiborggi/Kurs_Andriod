package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Question;
import vlad.model.Survey.TypeOfQuestionEnum;

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

    private TypeOfQuestionEnum typeOfQuestion;

    private List<AnswerForAttempt> answerForAttemptList;

    private float numFrom;

    private float numTo;

    public QuestionForAttempt(Question question){
        this.id =question.getId();
        this.text = question.getText();
        this.surveyId = question.getSurveyId();
        this.typeOfQuestion = question.getTypeOfQuestion();
        this.numFrom = question.getNumFrom();
        this.numTo = question.getNumFrom();

    }

    public float getNumFrom() {
        return numFrom;
    }

    public void setNumFrom(float numFrom) {
        this.numFrom = numFrom;
    }

    public float getNumTo() {
        return numTo;
    }

    public void setNumTo(float numTo) {
        this.numTo = numTo;
    }

    public TypeOfQuestionEnum getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestionEnum typeOfQuestionEnum) {
        this.typeOfQuestion = typeOfQuestionEnum;
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
