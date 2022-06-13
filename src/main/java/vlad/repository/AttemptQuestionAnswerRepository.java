package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.AttemptQuestionAnswer;

public interface AttemptQuestionAnswerRepository extends JpaRepository<AttemptQuestionAnswer, Long> {
    AttemptQuestionAnswer findById(long id);

}
