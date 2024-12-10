package bg.softuni.gameStore.services;

import bg.softuni.gameStore.dtos.*;
import bg.softuni.gameStore.models.Game;
import bg.softuni.gameStore.repositories.GameRepository;
import bg.softuni.gameStore.services.interfaces.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Game game = modelMapper.map(dto, Game.class);

        validateFields(game);

        gameRepository.save(game);

        return modelMapper.map(game, GameWithTitleDto.class);
    }

    @Override
    public boolean contains(long id) {
        return gameRepository.existsById(id);
    }

    @Override
    public GameEditDto getEditGame(long id) {
        Game game = getGame(id);

        return modelMapper.map(game, GameEditDto.class);
    }

    @Override
    public GamePurchaseDto getPurchaseGame(long id) {
        Game game = getGame(id);

        GamePurchaseDto gamePurchaseDto = modelMapper.map(game, GamePurchaseDto.class);

        return gamePurchaseDto;
    }

    @Override
    public GameWithTitleDto editGame(GameEditDto dto) {
        Game game = modelMapper.map(dto, Game.class);
        validateFields(game);
        gameRepository.save(game);

        return modelMapper.map(game, GameWithTitleDto.class);
    }

    @Override
    public GameWithTitleDto deleteGame(long id) {
        Game game = getGame(id);

        gameRepository.delete(game);

        return modelMapper.map(game, GameWithTitleDto.class);
    }

    @Override
    public List<GameWithTitleAndPriceDto> getAllGamesTitlesWithPrices() {
        List<Game> all = gameRepository.findAll();
        if (all.isEmpty()) {
            throw new IllegalArgumentException("No games are present in the database!");
        }

        return all
                .stream()
                .map(g -> modelMapper.map(g, GameWithTitleAndPriceDto.class))
                .toList();
    }

    @Override
    public List<GameDetailsDto> getGameDetails(String title) {
        List<Game> games = getGames(title);

        return games
                .stream()
                .map(g -> modelMapper.map(g, GameDetailsDto.class))
                .toList();
    }

    private List<Game> getGames(String title) {
        List<Game> games = gameRepository.findAllByTitle(title);

        if (games.isEmpty()) {
            throw new IllegalArgumentException("Id does not exist in the database");
        }
        return games;
    }

    private Game getGame(long id) {
        Optional<Game> game = gameRepository.findById(id);

        if (game.isEmpty()) {
            throw new IllegalArgumentException("Id does not exist in the database");
        }
        return game.get();
    }

    private static void validateFields(Game game) {
        if (game.getTitle().length() < 3 || game.getTitle().length() > 100 || Character.isLowerCase(game.getTitle().charAt(0))) {
            throw new IllegalArgumentException("Invalid game title!");
        }

        if (game.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("Invalid game price!");
        }

        if (game.getSize() < 0) {
            throw new IllegalArgumentException("Invalid game size!");
        }

        if (game.getTrailerId().length() != 11 && !game.getTrailerId().startsWith("https://www.youtube.com/watch?v=")) {
            throw new IllegalArgumentException("Invalid trailer!");
        }

        if (!game.getThumbnailUrl().startsWith("http://") && !game.getThumbnailUrl().startsWith("https://")) {
            throw new IllegalArgumentException("Invalid URL!");
        }

        if (game.getDescription().length() < 20) {
            throw new IllegalArgumentException("Invalid game description!");
        }

    }
}
