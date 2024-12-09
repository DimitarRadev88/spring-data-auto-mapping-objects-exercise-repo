package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserRegistrationDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserCommand implements Command {
    private static final String REGISTERED_MESSAGE = "%s was registered%n";
    private final UserService userService;
    private String[] commandData;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void execute() {
        UserRegistrationDto dto = new UserRegistrationDto();

        String email = commandData[0];
        String password = commandData[1];
        String confirmPassword = commandData[2];
        String fullName = commandData[3];

        dto.setEmail(email);
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);
        dto.setFullName(fullName);

        try {
            UserWithFullNameDto registered = userService.register(dto);
            System.out.printf(REGISTERED_MESSAGE, registered.getFullName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setData(String[] data) {
        this.commandData = data;
    }
}
