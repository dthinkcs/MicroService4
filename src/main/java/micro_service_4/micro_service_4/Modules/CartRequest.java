package micro_service_4.micro_service_4.Modules;

import java.util.List;
import java.util.UUID;

public class CartRequest {

    private UUID cartId;
    private List<ProductDetails> products;
    private AddressDetails address;
    private Integer totalCost;

    public CartRequest(){

    }

    public CartRequest(UUID cartId, List<ProductDetails> products, AddressDetails address, Integer totalCost) {
        this.cartId = cartId;
        this.products = products;
        this.address = address;
        this.totalCost = totalCost;
    }

    public AddressDetails getAddress() {
        return address;
    }

    public void setAddress(AddressDetails address) {
        this.address = address;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }



    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public List<ProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetails> products) {
        this.products = products;
    }





}
