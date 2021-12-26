package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Question;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDTO {

    private String text;
    private long surveyId;


    public static Question questionFromDTO (QuestionDTO questionDTO){
        Question ret = new Question();
        ret.setText(questionDTO.getText());
        ret.setSurveyId(questionDTO.getSurvetId());
        return ret;
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
