package micro_service_4.micro_service_4.Modules;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name="orderproductmap") //make table TODO
public class OrderProductMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;
    @Column(name="orderid")
    private UUID orderId;
    @Column(name="productid")
    private UUID productId;
    @Column(name="productname")
    private String productName;
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="price")
    private Integer price;

    public OrderProductMap() {
    }

    public OrderProductMap(UUID orderId, UUID productId, String productName, Integer quantity, Integer price) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
