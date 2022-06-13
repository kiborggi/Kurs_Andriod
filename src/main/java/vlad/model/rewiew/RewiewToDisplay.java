package vlad.model.rewiew;

import vlad.model.Survey.Question;

import java.util.ArrayList;
import java.util.List;

public class RewiewToDisplay {
    String name;
    String description;
    List<QuestionToDisplay> questionToDisplayList;


    public RewiewToDisplay(){
        questionToDisplayList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionToDisplay> getQuestionToDisplayList() {
        return questionToDisplayList;
    }

    public void setQuestionToDisplayList(List<QuestionToDisplay> questionToDisplayList) {
        this.questionToDisplayList = questionToDisplayList;
    }
}
