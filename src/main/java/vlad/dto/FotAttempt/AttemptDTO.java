package vlad.dto.FotAttempt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Status;
import vlad.model.Survey.Attempt;
import vlad.model.Survey.Question;

import javax.persistence.*;
import java.util.Date;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttemptDTO {

    private Long id;

    private  long userId;

    private  long surveyId;

    private Date created;

    private Date ended;

    private SurveyFotAttempt surveyFotAttempt;

    private Status status;
    public AttemptDTO (Attempt attempt){
        this.id = attempt.getId();
        this.userId = attempt.getUserId();
        this.surveyId = attempt.getSurveyId();
        this.created = attempt.getCreated();
        this.ended = attempt.getEnded();
        this.status = attempt.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }


    public SurveyFotAttempt getSurveyFotAttempt() {
        return surveyFotAttempt;
    }

    public void setSurveyFotAttempt(SurveyFotAttempt surveyFotAttempt) {
        this.surveyFotAttempt = surveyFotAttempt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
