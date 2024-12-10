package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameWithTitleAndPriceDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllGamesCommand extends CommandImpl {
    private final GameService gameService;

    public AllGamesCommand(GameService gameService1) {
        this.gameService = gameService1;
    }

    @Override
    public String execute() {
        List<GameWithTitleAndPriceDto> games = gameService.getAllGamesTitlesWithPrices();

        return games.stream()
                .map(GameWithTitleAndPriceDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
