package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.Answer;
import vlad.model.rewiew.Rewiev;

public interface RewiewRepository extends JpaRepository<Rewiev, Long> {

    Rewiev findById(long id);
}
