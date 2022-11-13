package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends JpaRepository<MoodEntity, Long> {
}
