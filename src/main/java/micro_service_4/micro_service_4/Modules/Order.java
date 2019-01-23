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
    @Id
    @Column(name="orderid")
    private UUID orderId ;
    @Column(name="dateofpurchase")
    private Date dateOfPurchase;
    @Column(name="addressid")
    private UUID addressId;
    @Column(name="totalcost")
    private Integer totalCost;
    @Column(name="isconfirmed")
    private Boolean isConfirmed;
    @Column(name="paymentid")
    private String paymentId;
    @Column(name="isordercancelled")
    private Boolean isOrderCancelled;

    public Boolean getIsOrderCancelled() {
        return isOrderCancelled;
    }

    public void setIsOrderCancelled(Boolean isOrderCancelled) {
        this.isOrderCancelled = isOrderCancelled;
    }



    public Order() {

    }

    public Order(UUID orderId, Date dateOfPurchase, UUID addressId, Integer totalCost, Boolean isConfirmed, String paymentId) {
        this.orderId = orderId;
        this.dateOfPurchase = dateOfPurchase;
        this.addressId = addressId;
        this.totalCost = totalCost;
        this.isConfirmed = isConfirmed;
        this.paymentId = paymentId;
    }


    public Boolean isConfirmed() {
        return isConfirmed;
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

    public Boolean getConfirmed() { return isConfirmed; }

    public String getPaymentId() { return paymentId; }

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

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

}
