package bg.softuni.gameStore.services;


import bg.softuni.gameStore.dtos.GamePurchaseDto;
import bg.softuni.gameStore.dtos.PurchaseOrderDto;
import bg.softuni.gameStore.dtos.UserLoggedInDto;
import bg.softuni.gameStore.models.Game;
import bg.softuni.gameStore.models.Order;
import bg.softuni.gameStore.models.User;
import bg.softuni.gameStore.repositories.GameRepository;
import bg.softuni.gameStore.repositories.OrderRepository;
import bg.softuni.gameStore.repositories.UserRepository;
import bg.softuni.gameStore.services.interfaces.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GameRepository gameRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PurchaseOrderDto purchase(List<Long> gameIds) {
        Optional<User> optionalUser = userRepository.findOneByLoggedInTrue();

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("You need to log in to purchase a game!");
        }

        List<Game> games = gameRepository.findAllById(gameIds);
        if (games.isEmpty()) {
            throw new IllegalArgumentException("Game ids do not exist in the database!");
        }

        User user = optionalUser.get();

        Order order = new Order();

        order.setBuyer(user);
        order.setProducts(games);

        List<Game> gameList = user.getGames();
        if (gameList == null) {
            gameList = new ArrayList<>();
        }

        gameList.addAll(games);

        orderRepository.save(order);
        userRepository.save(user);

        return modelMapper.map(order, PurchaseOrderDto.class);
    }
}

