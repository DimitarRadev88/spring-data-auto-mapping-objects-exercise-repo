package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserOwnedGamesDto;
import bg.softuni.gameStore.services.interfaces.UserService;

public class OwnedGamesCommand extends CommandImpl {
    private final UserService userService;

    public OwnedGamesCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute() {
        UserOwnedGamesDto user = userService.getUserWithOwnedGames();
        return user.toString();
    }
}
