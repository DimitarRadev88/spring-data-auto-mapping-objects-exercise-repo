package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.GameWithTitleDto;
import bg.softuni.gameStore.dtos.UserLoggedInDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AddGameCommand implements Command {
    private String[] commandData;
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public AddGameCommand(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public void execute() {
        UserLoggedInDto user;
        try {
            user = userService.getLoggedInUser();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (!user.getAdministrator()) {
            System.out.println("Only administrators can add games");
            return;
        }

        GameAddDto dto = new GameAddDto();
        dto.setTitle(commandData[0]);
        dto.setPrice(new BigDecimal(commandData[1]));
        dto.setSize(Double.parseDouble(commandData[2]));
        dto.setTrailer(commandData[3]);
        dto.setThumbnailUrl(commandData[4]);
        dto.setDescription(commandData[5]);
        dto.setReleaseDate(LocalDate.parse(commandData[6], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        try {
            GameWithTitleDto game = gameService.addGame(dto);
            System.out.println("Added " + game.getTitle());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void setData(String[] data) {
        this.commandData = data;
    }
}
