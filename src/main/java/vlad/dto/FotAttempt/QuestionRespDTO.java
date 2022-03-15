package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.TypeOfQuestionEnum;

import java.util.List;
import java.util.TreeSet;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionRespDTO {

    private long questionId;

    private TypeOfQuestionEnum typeOfQuestion;

    private List<Long> listAnswerId;

    private float numValue;


    public float getNumValue() {
        return numValue;
    }

    public void setNumValue(float numValue) {
        this.numValue = numValue;
    }

    public TypeOfQuestionEnum getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestionEnum typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }



    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public List<Long> getListAnswerId() {
        return listAnswerId;
    }

    public void setListAnswerId(List<Long> listAnswerId) {
        this.listAnswerId = listAnswerId;
    }
}
