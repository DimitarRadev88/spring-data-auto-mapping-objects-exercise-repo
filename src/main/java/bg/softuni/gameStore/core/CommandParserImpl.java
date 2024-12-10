package bg.softuni.gameStore.core;

import bg.softuni.gameStore.commands.*;
import bg.softuni.gameStore.enums.CommandType;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.OrderService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandParserImpl implements CommandParser {
    private final CommandExecutioner commandExecutioner;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public CommandParserImpl(CommandExecutioner commandExecutioner, UserService userService, GameService gameService, OrderService orderService) {
        this.commandExecutioner = commandExecutioner;
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void parse(String input) {
        String[] parts = input.split("\\|");

        CommandType command = CommandType.valueOf(parts[0]);

        String[] data = Arrays.stream(parts).skip(1).toArray(String[]::new);

        if (data.length > 0) {
            commandExecutioner.add(data);
        }

        try {
            String message = switch (command) {
                case RegisterUser -> commandExecutioner.execute(new RegisterUserCommand(userService));
                case LoginUser -> commandExecutioner.execute(new LogInUserCommand(userService));
                case Logout -> commandExecutioner.execute(new LogoutCommand(userService));
                case AddGame -> commandExecutioner.execute(new AddGameCommand(gameService, userService));
                case EditGame -> commandExecutioner.execute(new EditGameCommand(gameService, userService));
                case DeleteGame -> commandExecutioner.execute(new DeleteGameCommand(gameService, userService));
                case PurchaseGame -> commandExecutioner.execute(new PurchaseGameCommand(userService, gameService, orderService));
                case AllGames -> commandExecutioner.execute(new AllGamesCommand(gameService));
                case DetailGame -> commandExecutioner.execute(new DetailGameCommand(gameService));
                case OwnedGames -> commandExecutioner.execute(new OwnedGamesCommand(userService));
            };
            System.out.println(message);
        } catch (IllegalArgumentException | IllegalStateException | IllegalCallerException e) {
            System.out.println(e.getMessage());
        }


    }

}
