package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.AnswerTypeValue;

import java.util.List;

public interface AnswerTypeValueRepository extends JpaRepository<AnswerTypeValue, Long> {
    AnswerTypeValue findById(long id);
    List<AnswerTypeValue> findAllByAnswerId(long id);
}