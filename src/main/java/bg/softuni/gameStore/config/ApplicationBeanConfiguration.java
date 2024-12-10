package bg.softuni.gameStore.config;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.GameEditDto;
import bg.softuni.gameStore.dtos.PurchaseOrderDto;
import bg.softuni.gameStore.dtos.UserOwnedGamesDto;
import bg.softuni.gameStore.models.Game;
import bg.softuni.gameStore.models.Order;
import bg.softuni.gameStore.models.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class ApplicationBeanConfiguration {
    private static ModelMapper modelMapper;
    private static BufferedReader bufferedReader;

    @Bean
    public ModelMapper getMapperInstance() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
        }

        addMappings();

        return modelMapper;
    }

    private void addMappings() {
        stringToLocalDateTypeMap();
        gameAddDtoToGameTypeMap();
        gameEditDtoToGameTypeMap();
        orderToPurchaseOrderDtoTypeMap();
        UserToUserOwnedGamesDtoTypeMap();
    }

    private void UserToUserOwnedGamesDtoTypeMap() {
        TypeMap<User, UserOwnedGamesDto> typeMap = modelMapper.createTypeMap(User.class, UserOwnedGamesDto.class);

        typeMap
                .addMappings(mapper -> mapper.using(getGameConverter()).map(User::getGames, UserOwnedGamesDto::setGames));

    }

    private void orderToPurchaseOrderDtoTypeMap() {
        TypeMap<Order, PurchaseOrderDto> typeMap = modelMapper.createTypeMap(Order.class, PurchaseOrderDto.class);

        Converter<User, String> userConverter = c -> c.getSource() == null ? null : c.getSource().getFullName();

        typeMap.addMappings(mapper -> {
            mapper.using(userConverter).map(Order::getBuyer, PurchaseOrderDto::setBuyer);
            mapper.using(getGameConverter()).map(Order::getProducts, PurchaseOrderDto::setProducts);
        });
    }

    private static Converter<List<Game>, List<String>> getGameConverter() {
        return c -> c.getSource() == null ? null : c.getSource().stream().map(Game::getTitle).toList();
    }

    private static void stringToLocalDateTypeMap() {
        TypeMap<String, LocalDate> typeMap = modelMapper.createTypeMap(String.class, LocalDate.class);

        typeMap.setConverter(getDateConverter("yyyy-MM-dd"));
    }

    private void gameEditDtoToGameTypeMap() {
        TypeMap<GameEditDto, Game> typeMap = modelMapper.createTypeMap(GameEditDto.class, Game.class);

        typeMap.addMappings(mapper -> {
            mapper.using(getTrailerConverter()).map(GameEditDto::getTrailer, Game::setTrailerId);
        });
    }

    private static void gameAddDtoToGameTypeMap() {
        TypeMap<GameAddDto, Game> typeMap = modelMapper.createTypeMap(GameAddDto.class, Game.class);

        typeMap.addMappings(mapper -> {
            mapper.using(getTrailerConverter()).map(GameAddDto::getTrailer, Game::setTrailerId);
            mapper.using(getDateConverter("dd-MM-yyyy")).map(GameAddDto::getReleaseDate, Game::setReleaseDate);
        });
    }

    private static Converter<String, String> getTrailerConverter() {
        return c -> c.getSource().length() != 11 ?
                c.getSource().replace("https://www.youtube.com/watch?v=", "") :
                c.getSource();

    }

    private static Converter<String, LocalDate> getDateConverter(String pattern) {
        return c -> c.getSource() == null ?
                null :
                LocalDate.parse(c.getSource(), DateTimeFormatter.ofPattern(pattern));

    }


    @Bean
    public BufferedReader getReaderInstance() {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        return bufferedReader;
    }

}
