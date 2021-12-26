package vlad.model.Survey;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import vlad.model.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attempt")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private  long userId;

    @Column (name = "survey_id")
    private  long surveyId;

    @Column(name = "started")
    private Date created;

    @Column(name = "ended")
    private Date ended;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

    @Column (name = "result_text")
    private Status resultText;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Attempt (){
        this.status = Status.CREATED;
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
}
