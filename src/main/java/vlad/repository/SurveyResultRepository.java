package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.SurveyResult;

import java.util.List;

public interface SurveyResultRepository extends JpaRepository<SurveyResult,Long> {
    SurveyResult findById(long id);
    List<SurveyResult> findAllBySurveyId(long  id);
    List<SurveyResult> findAllByTypeId(long id);
}
