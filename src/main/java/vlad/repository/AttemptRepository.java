package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.Attempt;
import vlad.model.Survey.Question;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    Attempt findAllById(long id);
}
