package vlad.model.Survey;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "survey_id")
    private long surveyId;

    @Column(name = "type_of_question")
    private TypeOfQuestionEnum typeOfQuestion;

    @Column(name = "num_from")

    private float numFrom;

    @Column(name = "num_to")
    private float numTo;


    public Float getNumFrom() {
        return numFrom;
    }

    public void setNumFrom(float numFrom) {
        this.numFrom = numFrom;
    }

    public Float getNumTo() {
        return numTo;
    }

    public void setNumTo(float numTo) {
        this.numTo = numTo;
    }

    public TypeOfQuestionEnum getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestionEnum typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
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
}
