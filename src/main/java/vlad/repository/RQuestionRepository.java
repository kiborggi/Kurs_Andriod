package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.rewiew.Rquestion;

import java.util.List;

public interface RQuestionRepository extends JpaRepository<Rquestion, Long> {

    Rquestion findById(long id);
    List<Rquestion> findAllByRewievId(long id);
}