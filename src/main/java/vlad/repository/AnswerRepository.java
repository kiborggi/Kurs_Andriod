package vlad.repository;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import vlad.model.Survey.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findById(long id);

    List<Answer> findAllByQuestionId(long qid);
}
