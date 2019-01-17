package micro_service_4.micro_service_4.Modules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;
@Entity
@Table(name="cartordermap")
public class CartOrderMap {
    @Id
    @Column(name="orderid")
    private UUID orderId;
    @Column(name="cartid")
    private UUID cartId;

    public CartOrderMap(){

    }

    public CartOrderMap(UUID orderId, UUID cartId) {
        this.orderId = orderId;
        this.cartId = cartId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getCartId() {
        return cartId;
    }


}
