package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameDetailsDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailGameCommand extends CommandImpl {
    private final GameService gameService;

    public DetailGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute() {
        String title = getCommandData()[0];

        List<GameDetailsDto> list = gameService.getGameDetails(title);

        return list.stream().map(GameDetailsDto::toString).collect(Collectors.joining(System.lineSeparator() + "-" + System.lineSeparator()));
    }
}
