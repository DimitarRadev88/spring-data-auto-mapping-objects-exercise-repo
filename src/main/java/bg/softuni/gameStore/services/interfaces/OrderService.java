package bg.softuni.gameStore.services.interfaces;

import bg.softuni.gameStore.dtos.PurchaseOrderDto;

import java.util.List;

public interface OrderService {
    PurchaseOrderDto purchase(List<Long> gameIds);
}
