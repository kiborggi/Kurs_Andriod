package vlad.model.Survey;

import javax.persistence.*;

@Entity
@Table(name = "survey_result")
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "survey_id")
    private  long surveyId;

    @Column (name = "type_id")
    private  long typeId;

    @Column(name ="value_from")
    private float valueFrom;

    @Column(name ="value_to")
    private float valueTo;

    @Column (name = "text")
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
