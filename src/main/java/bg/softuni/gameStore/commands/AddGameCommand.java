package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.GameWithTitleDto;
import bg.softuni.gameStore.dtos.UserLoggedInDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AddGameCommand extends CommandImpl {
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public AddGameCommand(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public String execute() {
        UserLoggedInDto user = userService.getLoggedInUser();

        if (!user.getAdministrator()) {
            throw new IllegalCallerException("Only administrators can add games!");
        }

        GameAddDto dto = new GameAddDto();
        dto.setTitle(getCommandData()[0]);
        dto.setPrice(new BigDecimal(getCommandData()[1]));
        dto.setSize(Double.parseDouble(getCommandData()[2]));
        dto.setTrailer(getCommandData()[3]);
        dto.setThumbnailUrl(getCommandData()[4]);
        dto.setDescription(getCommandData()[5]);
        dto.setReleaseDate(getCommandData()[6]);

        GameWithTitleDto game = gameService.addGame(dto);
        return "Added " + game.getTitle();
    }

}
