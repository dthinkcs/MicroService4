package micro_service_4.micro_service_4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;
@Entity
@Table(name="orderproductmap")
public class OrderProductMap {
    @Id
    @Column(name="order_id")
    private UUID order_id;
    @Column(name="product_id")
    private UUID product_id;
    @Column(name="quantity")
    private int quantity;

    public OrderProductMap(){

    }

    public OrderProductMap(UUID order_id, UUID product_id, int quantity) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public UUID getOrder_id() {
        return order_id;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




}
