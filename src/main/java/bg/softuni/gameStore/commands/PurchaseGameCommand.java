package bg.softuni.gameStore.commands;

import bg.softuni.gameStore.dtos.PurchaseOrderDto;
import bg.softuni.gameStore.services.interfaces.GameService;
import bg.softuni.gameStore.services.interfaces.OrderService;
import bg.softuni.gameStore.services.interfaces.UserService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PurchaseGameCommand extends CommandImpl {
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    public PurchaseGameCommand(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public String execute() {
        List<Long> list = Arrays.stream(getCommandData()).map(Long::parseLong).toList();
        PurchaseOrderDto purchase = orderService.purchase(list);

        return purchase.toString();
    }
}
