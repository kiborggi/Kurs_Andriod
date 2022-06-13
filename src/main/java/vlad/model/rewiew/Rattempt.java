package vlad.model.rewiew;


import vlad.model.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rattempt")
public class Rattempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "survey_id")
    private  long surveyId;

    @Column (name = "user_name")
    private String userName;

    @Column(name = "started")
    private Date created;

    @Column(name = "ended")
    private Date ended;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
