package bg.softuni.gameStore.core;

import bg.softuni.gameStore.commands.AddGameCommand;
import bg.softuni.gameStore.commands.LogInUserCommand;
import bg.softuni.gameStore.commands.LogoutCommand;
import bg.softuni.gameStore.commands.RegisterUserCommand;
import bg.softuni.gameStore.enums.CommandType;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.OrderService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
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
    public void parse(String input) throws OperationNotSupportedException {
        String[] parts = input.split("\\|");

        CommandType command = CommandType.valueOf(parts[0]);

        String[] data = Arrays.stream(parts).skip(1).toArray(String[]::new);

        if (data.length > 0) {
            commandExecutioner.add(data);
        }

        switch (command) {
            case RegisterUser -> commandExecutioner.execute(new RegisterUserCommand(userService));
            case LoginUser -> commandExecutioner.execute(new LogInUserCommand(userService));
            case Logout -> commandExecutioner.execute(new LogoutCommand(userService));
            case AddGame -> commandExecutioner.execute(new AddGameCommand(gameService, userService));
        }

    }

}
