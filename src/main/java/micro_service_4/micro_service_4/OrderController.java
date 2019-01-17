package micro_service_4.micro_service_4;

import micro_service_4.micro_service_4.Modules.AddressDetails;
import micro_service_4.micro_service_4.Modules.CartRequest;
import micro_service_4.micro_service_4.Modules.PaymentRequest;
import micro_service_4.micro_service_4.Modules.ProductDetails;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderService;
import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class OrderController {
    @Autowired
 private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;



   @RequestMapping(method = RequestMethod.POST, value = "/addCartEntry/")
     public void addCartEntry(@RequestBody CartRequest request) throws Exception{

       AddressDetails addressDetails = request.getAddress();
       addressDetails.setAddressId(UUID.randomUUID());
       addressDetailsService.add_addressdetails(addressDetails);
       orderService.foo(request.getCartId(),request.getProducts(),addressDetails,request.getTotalCost());


    }
   @RequestMapping(method=RequestMethod.POST,value = "/orderConfirmation/")
    public Response confirmPaymentForOrder(@RequestBody PaymentRequest request){

       Response response = orderService.bar(request.getCartId(),request.getPaymentId(),request.getDateOfPurchase(),request.getModeOfPayment(),request.getSuccess());

        return response;
   }


}
