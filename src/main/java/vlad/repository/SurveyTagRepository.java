package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.Answer;
import vlad.model.Survey.SurveyTag;

public interface SurveyTagRepository extends JpaRepository<SurveyTag, Long> {
}
