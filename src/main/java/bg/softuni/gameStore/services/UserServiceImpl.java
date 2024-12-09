package bg.softuni.gameStore.services;

import bg.softuni.gameStore.dtos.UserLogInDto;
import bg.softuni.gameStore.dtos.UserLoggedInDto;
import bg.softuni.gameStore.dtos.UserRegistrationDto;
import bg.softuni.gameStore.dtos.UserWithFullNameDto;
import bg.softuni.gameStore.models.User;
import bg.softuni.gameStore.repositories.UserRepository;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final List<User> loggedIn;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.loggedIn = new ArrayList<>();
    }

    @Override
    public UserWithFullNameDto register(UserRegistrationDto dto) {
        validateUserRegistrationData(dto);

        User user = modelMapper.map(dto, User.class);

        if (userRepository.count() == 0) {
            user.setAdministrator(true);
        }
        user.setLoggedIn(false);

        userRepository.save(user);

        return modelMapper.map(user, UserWithFullNameDto.class);
    }

    private void validateUserRegistrationData(UserRegistrationDto dto) {
        Pattern pattern = Pattern.compile("^\\w+@\\w+\\.\\w+$");
        Matcher matcher = pattern.matcher(dto.getEmail());

        if (!matcher.find()) {
            throw new IllegalArgumentException("Email should contain '@' and '.'!");
        }

        pattern = Pattern.compile("(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)");
        matcher = pattern.matcher(dto.getPassword());

        if (!matcher.find()) {
            throw new IllegalArgumentException("Password must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit");
        }

        if (userRepository.existsUserByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists in database!");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
    }

    @Override
    public UserWithFullNameDto logIn(UserLogInDto dto) {
        User user = modelMapper.map(dto, User.class);

        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Incorrect username / password!");
        }

        User savedUser = optionalUser.get();

        if (savedUser.getPassword().equals(user.getPassword())) {
            savedUser.setLoggedIn(true);
            userRepository.save(savedUser);
        } else {
            throw new IllegalArgumentException("Incorrect username / password!");
        }

        return modelMapper.map(savedUser, UserWithFullNameDto.class);
    }

    @Override
    public UserWithFullNameDto logOut() {
        Optional<User> user = userRepository.findOneByLoggedInTrue();
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Cannot log out. No user was logged in.");
        }


        return modelMapper.map(user.get(), UserWithFullNameDto.class);
    }

    @Override
    public UserLoggedInDto getLoggedInUser() {
        Optional<User> user = userRepository.findOneByLoggedInTrue();
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user is logged in!");
        }
        return modelMapper.map(user.get(), UserLoggedInDto.class);
    }

}
