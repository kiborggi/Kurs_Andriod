package vlad.model.rewiew;

import vlad.model.Status;
import vlad.model.Survey.Question;

import java.util.Iterator;
import java.util.List;

public class RewiewSession {

    long RewId;
    Question currentQuestion;
    Iterator<Question> rquestionIterator;
    String name;

    String status;

    Rattempt rattempt;

    List< RattemptQuestionAnswer> rattemptQuestionAnswerList;


    public RewiewSession(){
        this.status = Status.ACTIVE.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RattemptQuestionAnswer> getRattemptQuestionAnswerList() {
        return rattemptQuestionAnswerList;
    }

    public void setRattemptQuestionAnswerList(List<RattemptQuestionAnswer> rattemptQuestionAnswerList) {
        this.rattemptQuestionAnswerList = rattemptQuestionAnswerList;
    }

    public Rattempt getRattempt() {
        return rattempt;
    }

    public void setRattempt(Rattempt rattempt) {
        this.rattempt = rattempt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRewId() {
        return RewId;
    }

    public void setRewId(long rewId) {
        RewId = rewId;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Iterator<Question> getRquestionIterator() {
        return rquestionIterator;
    }

    public void setRquestionIterator(Iterator<Question> questionIterator) {
        this.rquestionIterator = questionIterator;
    }
}
