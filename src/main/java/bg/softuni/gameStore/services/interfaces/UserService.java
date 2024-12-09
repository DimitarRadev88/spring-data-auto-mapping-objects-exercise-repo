package bg.softuni.gameStore.services.interfaces;

import bg.softuni.gameStore.dtos.UserLogInDto;
import bg.softuni.gameStore.dtos.UserLoggedInDto;
import bg.softuni.gameStore.dtos.UserRegistrationDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;

public interface UserService {

    UserWithFullNameDto register(UserRegistrationDto dto);

    UserWithFullNameDto logIn(UserLogInDto dto);

    UserWithFullNameDto logOut();

    UserLoggedInDto getLoggedInUser();
}
