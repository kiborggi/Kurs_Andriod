package vlad.model.Survey;

import javax.persistence.*;

@Entity
@Table(name = "answer_type_value")
public class AnswerTypeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_id")
    private long answerId;

    @Column(name = "type_id")
    private long typeId;

    @Column(name = "value")
    private float value;

    public Long getId() {
        return id;
    }

    public long getAnswerID() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeID) {
        this.typeId = typeID;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
