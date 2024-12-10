package bg.softuni.gameStore.services.interfaces;

import bg.softuni.gameStore.dtos.*;

public interface UserService {

    UserWithFullNameDto register(UserRegistrationDto dto);

    UserWithFullNameDto logIn(UserLogInDto dto);

    UserWithFullNameDto logOut();

    UserLoggedInDto getLoggedInUser();

    UserOwnedGamesDto getUserWithOwnedGames();
}
