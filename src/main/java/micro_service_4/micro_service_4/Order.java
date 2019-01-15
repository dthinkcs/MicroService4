package micro_service_4.micro_service_4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name="ordertable")
public class Order {
    @Id
    @Column(name="order_id")
    private UUID order_id ;
    @Column(name="date_of_purchase")
    private Date date_of_purchase;
    @Column(name="address")
    private String address;
    @Column(name="total_cost")
    private Integer total_cost;

    public Order() {
    }

    public Order(UUID order_id, Date date_of_purchase, String address, Integer total_cost) {
        this.order_id = order_id;
        this.date_of_purchase = date_of_purchase;
        this.address = address;
        this.total_cost = total_cost;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public void setDate_of_purchase(Date date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getOrder_id() {
        return order_id;
    }

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public String getAddress() {
        return address;
    }

    public Integer getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Integer total_cost) {
        this.total_cost = total_cost;
    }
}
