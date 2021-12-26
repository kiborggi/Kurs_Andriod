package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.TreeSet;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionRespDTO {

    private long questionId;

    private List<Long> listAnswerId;

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
