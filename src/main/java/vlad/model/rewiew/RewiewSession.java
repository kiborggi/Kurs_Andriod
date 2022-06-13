package vlad.model.rewiew;

import java.util.Iterator;

public class RewiewSession {
    private long rewId;
    private Iterator<Rquestion> rquestionIterator;
    private String name;

    public long getRewId() {
        return rewId;
    }

    public void setRewId(long rewId) {
        this.rewId = rewId;
    }

    public Iterator<Rquestion> getRquestionIterator() {
        return rquestionIterator;
    }

    public void setRquestionIterator(Iterator<Rquestion> rquestionIterator) {
        this.rquestionIterator = rquestionIterator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
