package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.UserRegistrationDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserCommand extends CommandImpl {
    private static final String REGISTERED_MESSAGE = "%s was registered";
    private final UserService userService;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute() {
        UserRegistrationDto dto = new UserRegistrationDto();

        String email = getCommandData()[0];
        String password = getCommandData()[1];
        String confirmPassword = getCommandData()[2];
        String fullName = getCommandData()[3];

        dto.setEmail(email);
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);
        dto.setFullName(fullName);

        UserWithFullNameDto registered = userService.register(dto);
        return String.format(REGISTERED_MESSAGE, registered.getFullName());
    }

}
