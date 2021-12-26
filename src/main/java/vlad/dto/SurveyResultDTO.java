package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.SurveyResult;

import javax.persistence.Column;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyResultDTO {

    private  long surveyId;

    private  long typeId;

    private float valueFrom;

    private float valueTo;

    private String text;

 public static SurveyResult getSurveyResultFromDTO(SurveyResultDTO surveyResultDTO){
        SurveyResult ret = new SurveyResult();
        ret.setSurveyId(surveyResultDTO.surveyId);
        ret.setTypeId(surveyResultDTO.getTypeId());
        ret.setValueFrom(surveyResultDTO.getValueFrom());
        ret.setValueTo(surveyResultDTO.getValueTo());
        ret.setText(surveyResultDTO.getText());
        return ret;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public float getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(float valueFrom) {
        this.valueFrom = valueFrom;
    }

    public float getValueTo() {
        return valueTo;
    }

    public void setValueTo(float valueTo) {
        this.valueTo = valueTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
