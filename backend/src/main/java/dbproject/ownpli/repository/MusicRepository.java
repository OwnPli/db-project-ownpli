package dbproject.ownpli.repository;

import dbproject.ownpli.domain.music.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, String> {

}
