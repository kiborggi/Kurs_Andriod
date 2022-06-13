package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.rewiew.Rattempt;
import vlad.model.rewiew.RattemptQuestionAnswer;

import java.util.List;

public interface RattemptQuestionAnswerRepository extends JpaRepository<RattemptQuestionAnswer, Long> {
    RattemptQuestionAnswer findById(long id);

    List<RattemptQuestionAnswer> findAllByQuestionId(long id);

    List<RattemptQuestionAnswer> findAllByAnswerId(long id);
}
