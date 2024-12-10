package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameWithTitleDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class DeleteGameCommand extends CommandImpl {
    private final GameService gameService;
    private final UserService userService;

    public DeleteGameCommand(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public String execute() {
        if (!userService.getLoggedInUser().getAdministrator()) {
            throw new IllegalCallerException("Only administrators can delete games!");
        }

        GameWithTitleDto withTitleDto = gameService.deleteGame(Long.parseLong(getCommandData()[0]));
        return "Deleted " + withTitleDto.getTitle();
    }

}
