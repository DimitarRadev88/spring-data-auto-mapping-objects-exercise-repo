package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserLogInDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogInUserCommand extends CommandImpl {
    private static final String LOG_IN_MESSAGE = "Successfully logged in %s";
    private final UserService userService;

    @Autowired
    public LogInUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute() {
        UserLogInDto dto = new UserLogInDto();

        dto.setEmail(getCommandData()[0]);
        dto.setPassword(getCommandData()[1]);

        UserWithFullNameDto loggedIn = userService.logIn(dto);
        return String.format(LOG_IN_MESSAGE, loggedIn.getFullName());
    }

}
