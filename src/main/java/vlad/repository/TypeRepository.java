package vlad.repository;


import vlad.model.Survey.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findById(long id);
    List<Type> findAllBySurveyId(long id);
}
