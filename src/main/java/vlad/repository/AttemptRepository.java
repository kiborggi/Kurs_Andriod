package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Status;
import vlad.model.Survey.Attempt;
import vlad.model.Survey.Question;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    Attempt findAllById(long id);
    List<Attempt> findAllBySurveyIdAndStatusAndUserId(long id, Status status,long userId );
    List<Attempt> findAllBySurveyIdAndStatus(long id, Status status);
    List<Attempt> findAllBySurveyId(long id);
}
