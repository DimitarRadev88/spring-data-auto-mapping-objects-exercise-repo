package bg.softuni.gameStore.services.interfaces;

import bg.softuni.gameStore.dtos.*;

import java.util.List;

public interface GameService {
    GameWithTitleDto addGame(GameAddDto dto);

    boolean contains(long id);

    GameEditDto getEditGame(long id);

    GamePurchaseDto getPurchaseGame(long id);

    GameWithTitleDto editGame(GameEditDto dto);

    GameWithTitleDto deleteGame(long id);

    List<GameWithTitleAndPriceDto> getAllGamesTitlesWithPrices();

    List<GameDetailsDto> getGameDetails(String title);
}
