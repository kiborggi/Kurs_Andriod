package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.rewiew.Rattempt;

public interface RattemptRepository extends JpaRepository<Rattempt, Long> {
    Rattempt findById(long id);
}
