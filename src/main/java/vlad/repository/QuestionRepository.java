package vlad.repository;

import vlad.model.Survey.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionsById(long id);
    List<Question> findQuestionsBySurveyId(long survId);
 }
