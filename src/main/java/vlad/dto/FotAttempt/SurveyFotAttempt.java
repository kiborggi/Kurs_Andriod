package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Survey;

import javax.persistence.Column;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyFotAttempt {
    private Long id;

    private String description;

    private long ownerId;

    private String name;

    private List<QuestionForAttempt> questionForAttemptList;


    public SurveyFotAttempt (Survey survey){
        this.id = survey.getId();
        this.description = survey.getDescription();
        this.name = survey.getName();
        this.ownerId = survey.getOwnerId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionForAttempt> getQuestionForAttemptList() {
        return questionForAttemptList;
    }

    public void setQuestionForAttemptList(List<QuestionForAttempt> questionForAttemptList) {
        this.questionForAttemptList = questionForAttemptList;
    }
}
