package bg.softuni.gameStore.services;

import bg.softuni.gameStore.dtos.GameAddDto;
import bg.softuni.gameStore.dtos.GameWithTitleDto;
import bg.softuni.gameStore.models.Game;
import bg.softuni.gameStore.repositories.GameRepository;
import bg.softuni.gameStore.services.interfaces.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GameWithTitleDto addGame(GameAddDto dto) {
        validateFields(dto);

        Game game = modelMapper.map(dto, Game.class);

        gameRepository.save(game);

        return modelMapper.map(game, GameWithTitleDto.class);
    }

    private static void validateFields(GameAddDto dto) {
        if (dto.getTitle().length() < 3 || dto.getTitle().length() > 100 || Character.isLowerCase(dto.getTitle().charAt(0))) {
            throw new IllegalArgumentException("Invalid game title!");
        }

        if (dto.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("Invalid game price!");
        }

        if (dto.getSize() < 0) {
            throw new IllegalArgumentException("Invalid game size!");
        }

        if (dto.getTrailer().length() != 11 && !dto.getTrailer().startsWith("https://www.youtube.com/watch?v=")) {
            throw new IllegalArgumentException("Invalid trailer!");
        }

        if (!dto.getThumbnailUrl().startsWith("http://") && !dto.getThumbnailUrl().startsWith("https://")) {
            throw new IllegalArgumentException("Invalid URL!");
        }

        if (dto.getDescription().length() < 20) {
            throw new IllegalArgumentException("Invalid game description!");
        }

    }
}
