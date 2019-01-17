package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.CartOrderMap;
import micro_service_4.micro_service_4.Repository.CartOrderMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartOrderMapService {
    @Autowired
    private CartOrderMapRepository cartordermaprepository;
    public void add_mapcartorder(CartOrderMap cartordermap){
        cartordermaprepository.save(cartordermap);
    }

    public void removeEntryForOrderId(UUID orderId){

       cartordermaprepository.findById(orderId)
               .map(cartOrderMap -> {
                   cartordermaprepository.delete(cartOrderMap);
                   return ResponseEntity.ok().build();
               });


    }


    public UUID getOrderIdFromCartId(UUID cartId){

        CartOrderMap cartOrderMap = cartordermaprepository.findByCartId(cartId);
        System.out.println("order id is " + cartOrderMap.getOrderId());
        return cartOrderMap.getOrderId();

    }
}
