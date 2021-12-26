package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Answer;

import javax.persistence.Column;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerDTO {
    private long questionId;
    private String text;

    public static Answer getAnswerFronDTO(AnswerDTO answerDTO){
        Answer ret = new Answer();
        ret.setQuestionId(answerDTO.getQuestionId());
        ret.setText(answerDTO.getText());
        return ret;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
