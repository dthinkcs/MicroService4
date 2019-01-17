package micro_service_4.micro_service_4.Modules;

import java.util.Date;
import java.util.UUID;

public class PaymentRequest {

    private UUID paymentId;
    private UUID cartId;
    private Boolean isSuccess;
    private String modeOfPayment;
    private Date dateOfPurchase;

    public PaymentRequest(){

    }

    public PaymentRequest(UUID paymentId, UUID cartId, Boolean isSuccess, String modeOfPayment, Date dateOfPurchase) {
        this.paymentId = paymentId;
        this.cartId = cartId;
        this.isSuccess = isSuccess;
        this.modeOfPayment = modeOfPayment;
        this.dateOfPurchase = dateOfPurchase;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }



}
