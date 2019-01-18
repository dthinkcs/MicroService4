package micro_service_4.micro_service_4.Modules;

import java.util.UUID;

public class CartRequestResponse {

    private UUID orderId;

    public CartRequestResponse(){

    }

    public CartRequestResponse(UUID orderId) {
        this.orderId = orderId;
    }



    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
