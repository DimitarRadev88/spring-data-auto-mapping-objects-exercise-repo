package bg.softuni.gameStore.dtos;

import java.util.List;

public class PurchaseOrderDto {

    private String buyer;
    private List<String> products;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return String.format("User %s purchased - %s", buyer, String.join(", ", products));
    }
}
