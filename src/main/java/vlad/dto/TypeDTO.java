package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Type;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeDTO {
    long surveyId;
    private String text;

    public static Type getTypeFromDTO(TypeDTO typeDTO){
        Type ret = new Type();
        ret.setText(typeDTO.getText());
        ret.setSurveyId(typeDTO.getSurveyId());
        return ret;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
