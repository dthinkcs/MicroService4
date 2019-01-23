package micro_service_4.micro_service_4.Modules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name="ordertable") // table make TODO
public class Order {
    public enum Status {
        Awaiting ,Confirmed , Cancelled , OutForDelivery
    }
    @Id
    @Column(name="orderid")
    private UUID orderId ;
    @Column(name="dateofpurchase")
    private Date dateOfPurchase;
    @Column(name="addressid")
    private UUID addressId;
    @Column(name="totalcost")
    private Integer totalCost;
    @Column(name="paymentid")
    private String paymentId;
    @Column(name="status")
    private Status status;

    public Order() {

    }

    public Order(UUID orderId, Date dateOfPurchase, UUID addressId, Integer totalCost, String paymentId, Status status) {
        this.orderId = orderId;
        this.dateOfPurchase = dateOfPurchase;
        this.addressId = addressId;
        this.totalCost = totalCost;
        this.paymentId = paymentId;
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
