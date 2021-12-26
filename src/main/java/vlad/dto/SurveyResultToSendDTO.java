package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.SurveyResult;
import vlad.repository.TypeRepository;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyResultToSendDTO {
    private  long surveyId;

    private  long typeId;

    private float valueFrom;

    private float valueTo;

    private String text;

    private String typeText;

    public SurveyResultToSendDTO (SurveyResult surveyResult, TypeRepository typeRepository){
        this.surveyId =surveyResult.getSurveyId();
        this.text = surveyResult.getText();
        this.valueFrom = surveyResult.getValueFrom();
        this.valueTo =surveyResult.getValueTo();
        this.text = surveyResult.getText();
        this.typeText = typeRepository.findById(surveyResult.getTypeId()).getText();
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
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
