package bg.softuni.gameStore.services.interfaces;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.GameWithTitleDto;

public interface GameService {
    GameWithTitleDto addGame(GameAddDto dto);
}
