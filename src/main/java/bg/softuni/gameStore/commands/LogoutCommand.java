package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommand extends CommandImpl {
    private static final String LOG_OUT_MESSAGE = "User %s successfully logged out";
    private final UserService userService;

    public LogoutCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute() {
        UserWithFullNameDto dto = userService.logOut();
        return String.format(LOG_OUT_MESSAGE, dto.getFullName());
    }

}
