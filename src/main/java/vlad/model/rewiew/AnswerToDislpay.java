package vlad.model.rewiew;

import vlad.model.Survey.Question;

import java.util.ArrayList;
import java.util.List;

public class AnswerToDislpay {
    String text;

    int votes;

    List<String> people;

    double percent;

    public String getPercentStr(){
        return "Проценты: " + Math.round(percent);
    }

    public String getPeopleStr(){
        String ret = "Проголосовали: ";
        for (String p : people){
            ret += p +", ";
        }
        return  ret;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public AnswerToDislpay(){
        people = new ArrayList<String>();
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
