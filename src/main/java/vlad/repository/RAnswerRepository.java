package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.Answer;
import vlad.model.rewiew.Ranswer;

public interface RAnswerRepository extends JpaRepository<Ranswer, Long> {
    Ranswer findById(long id);

}
