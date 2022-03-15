package vlad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlad.model.Survey.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
