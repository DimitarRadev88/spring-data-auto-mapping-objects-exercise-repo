package bg.softuni.gameStore.core;

import bg.softuni.gameStore.dtos.UserRegistrationDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.OrderService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    CommandParser parser;
    private final BufferedReader reader;

    @Autowired
    public ConsoleRunner(BufferedReader reader, CommandParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = reader.readLine();
        while (!input.isBlank()) {
            parser.parse(input);
            input = reader.readLine();
        }
    }


}
