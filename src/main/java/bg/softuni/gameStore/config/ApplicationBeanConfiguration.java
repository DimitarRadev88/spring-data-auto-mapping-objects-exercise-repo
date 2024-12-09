package bg.softuni.gameStore.config;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.UserLogInDto;
import bg.softuni.gameStore.models.Game;
import bg.softuni.gameStore.models.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        TypeMap<GameAddDto, Game> typeMap = modelMapper.createTypeMap(GameAddDto.class, Game.class);

        Converter<String, String> trailerConverter = c -> c.getSource().length() != 11 ?
                c.getSource().replace( "https://www.youtube.com/watch?v=", "") :
                c.getSource();

        typeMap.addMappings(mapper -> mapper.using(trailerConverter).map(GameAddDto::getTrailer, Game::setTrailerId));
    }


    @Bean
    public BufferedReader getReaderInstance() {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        return bufferedReader;
    }

}
