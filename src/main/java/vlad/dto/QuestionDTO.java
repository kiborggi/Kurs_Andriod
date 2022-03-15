package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Question;
import vlad.model.Survey.TypeOfQuestionEnum;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {

    private String text;
    private long surveyId;
    public TypeOfQuestionEnum typeOfQuestion;
    private float numFrom;
    private float numTo;

    public static Question questionFromDTO (QuestionDTO questionDTO){
        Question ret = new Question();
        ret.setText(questionDTO.getText());
        ret.setSurveyId(questionDTO.getSurvetId());
        ret.setTypeOfQuestion(questionDTO.getTypeOfQuestion());
        if(questionDTO.getTypeOfQuestion().equals(TypeOfQuestionEnum.NUMERIC)){

            ret.setNumFrom(questionDTO.getNumFrom());
            ret.setNumTo(questionDTO.getNumTo());
        }

        return ret;
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

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public TypeOfQuestionEnum getTypeOfQuestion() {


        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestionEnum typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSurvetId() {
        return surveyId;
    }

    public void setSurvetId(long survetId) {
        this.surveyId = survetId;
    }
}
