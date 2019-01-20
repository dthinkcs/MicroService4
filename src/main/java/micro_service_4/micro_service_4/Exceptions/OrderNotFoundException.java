package micro_service_4.micro_service_4.Exceptions;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message){
        super(message);
    }


    public OrderNotFoundException(UUID orderId){
        super("Order with ID - " + orderId.toString() + " not found");
    }

}
