package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.GameEditDto;
import bg.softuni.gameStore.dtos.GameWithTitleDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class EditGameCommand extends CommandImpl {
    private static final String EDITED_GAME_MESSAGE = "Edited %s";
    private final GameService gameService;
    private final UserService userService;

    public EditGameCommand(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public String execute() {
        long id = Long.parseLong(getCommandData()[0]);

        GameEditDto game = gameService.getEditGame(id);

        if (!userService.getLoggedInUser().getAdministrator()) {
            throw new IllegalCallerException("Only administrators can edit games!");
        }

        editGameInfo(game);

        GameWithTitleDto dto = gameService.editGame(game);

        return String.format(EDITED_GAME_MESSAGE, dto.getTitle());
    }

    private void editGameInfo(GameEditDto game) {

        Arrays.stream(getCommandData()).skip(1).forEach(row -> {
            String[] fieldAndValue = row.split("=");
            String fieldType = fieldAndValue[0];
            String value = fieldAndValue[1];

            switch (fieldType) {
                case "title" -> game.setTitle(value);
                case "price" -> game.setPrice(new BigDecimal(value));
                case "size" -> game.setSize(Double.parseDouble(value));
                case "trailer" -> game.setTrailer(value);
                case "thumbnailURL" -> game.setThumbnailUrl(value);
                case "description" -> game.setDescription(value);
                case "releaseDate" -> game.setReleaseDate(value);
            }
        });
    }

}
