package vlad.repository;

import org.springframework.stereotype.Repository;
import vlad.model.Status;
import vlad.model.Survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Survey findSurveyById(long id);

    List<Survey> findSurveyByOwnerId(long id);
    List<Survey> findSurveyByStatus( Status status);
    List<Survey> findAllByStatus(Status status);
}
