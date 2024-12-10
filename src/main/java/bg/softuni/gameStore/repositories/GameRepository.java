package bg.softuni.gameStore.repositories;

import bg.softuni.gameStore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsById(long id);

    List<Game> findAllByTitle(String title);

}
