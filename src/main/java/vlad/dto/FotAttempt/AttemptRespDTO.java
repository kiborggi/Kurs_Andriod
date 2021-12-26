package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttemptRespDTO {

    private long attemptId;

    private long surveyId;

    private List<QuestionRespDTO> listQuestion;

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public List<QuestionRespDTO> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<QuestionRespDTO> listQuestion) {
        this.listQuestion = listQuestion;
    }
}
