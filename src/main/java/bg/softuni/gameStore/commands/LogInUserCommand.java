package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserLogInDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogInUserCommand implements Command {
    private static final String LOG_IN_MESSAGE = "Successfully logged in %s%n";

    private final UserService userService;
    private String[] commandData;

    @Autowired
    public LogInUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        UserLogInDto dto = new UserLogInDto();

        dto.setEmail(commandData[0]);
        dto.setPassword(commandData[1]);

        try {
            UserWithFullNameDto loggedIn = userService.logIn(dto);
            System.out.printf(LOG_IN_MESSAGE, loggedIn.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setData(String[] data) {
        this.commandData = data;
    }
}
