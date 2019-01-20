package micro_service_4.micro_service_4.Modules;

import micro_service_4.micro_service_4.Exceptions.InvalidParameterException;

import java.util.UUID;

public class OrderSummaryRequest {
    private UUID orderId;

    public OrderSummaryRequest(){

    }

    public OrderSummaryRequest(UUID orderId) {
        this.orderId = orderId;
    }



    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId){
            this.orderId = orderId;

    }
}