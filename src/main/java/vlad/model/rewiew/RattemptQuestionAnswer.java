package vlad.model.rewiew;

import javax.persistence.*;

@Entity
@Table(name = "rattempt_question_answer")
public class RattemptQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "attempt_id")
    private  long attemptId;

    @Column (name = "answer_id")
    private  long answerId;

    @Column (name = "question_id")
    private  long questionId;

    @Column (name = "num_value")
    private  long numValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getNumValue() {
        return numValue;
    }

    public void setNumValue(long numValue) {
        this.numValue = numValue;
    }
}
