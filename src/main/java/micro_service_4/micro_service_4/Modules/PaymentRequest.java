package micro_service_4.micro_service_4.Modules;

import micro_service_4.micro_service_4.Exceptions.InvalidParameterException;

import java.util.Date;
import java.util.UUID;

public class PaymentRequest {

    private String paymentId;
    private UUID orderId;
    private Boolean isSuccess;
    private String modeOfPayment;
    private Date dateOfPurchase;

    public PaymentRequest(){

    }

    public PaymentRequest(String paymentId, UUID orderId, Boolean isSuccess, String modeOfPayment, Date dateOfPurchase) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.isSuccess = isSuccess;
        this.modeOfPayment = modeOfPayment;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId)throws Exception{
        this.orderId = orderId;
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
