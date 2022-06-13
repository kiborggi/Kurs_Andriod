package vlad.model.rewiew;

import java.util.ArrayList;
import java.util.List;

public class QuestionToDisplay {
    String text;
    int votes;

    List<AnswerToDislpay> answerToDislpayList;


    public String getVotesStr(){
        return "Голоса: " + votes;
    }

    public QuestionToDisplay (){
        answerToDislpayList = new ArrayList<>();
    }

    public List<AnswerToDislpay> getAnswerToDislpayList() {
        return answerToDislpayList;
    }

    public void setAnswerToDislpayList(List<AnswerToDislpay> answerToDislpayList) {
        this.answerToDislpayList = answerToDislpayList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }


}
