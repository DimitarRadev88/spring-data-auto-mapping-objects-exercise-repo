package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;

public class LogoutCommand implements Command {
    private static final String LOG_OUT_MESSAGE = "User %s successfully logged out%n";
    private final UserService userService;

    public LogoutCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        try {
            UserWithFullNameDto dto = userService.logOut();
            System.out.printf(LOG_OUT_MESSAGE, dto.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setData(String[] data) {

    }
}
