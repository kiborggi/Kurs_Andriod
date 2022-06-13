package vlad.model.rewiew;

import vlad.model.Survey.TypeOfQuestionEnum;

import javax.persistence.*;

@Entity
@Table(name = "rquestion")
public class Rquestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "rewiew_id")
    private long rewiewId;

    @Column(name = "type_of_question")
    private TypeOfQuestionEnum typeOfQuestion;

    public long getRewiewId() {
        return rewiewId;
    }

    public void setRewiewId(long rewiewId) {
        this.rewiewId = rewiewId;
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


}
