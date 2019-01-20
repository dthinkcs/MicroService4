package micro_service_4.micro_service_4.Controllers;

import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;


    @RequestMapping(method = RequestMethod.POST, value = "/addCartEntry/")
    public CartRequestResponse addCartEntry(@RequestBody CartRequest request) throws Exception {

        if(request.getProducts().size() == 0)
            throw new Exception(new InvalidParameterException("No products Found"));

        if(request.getTotalCost() == 0)
            throw new Exception(new InvalidParameterException("Zero total Cost not allowed"));

        if(request.getAddress().getPinCode() == null || request.getAddress().getPinCode().equals(""))
            throw new Exception(new InvalidParameterException("Pin Code required"));

        for(ProductDetails prodDetail: request.getProducts()){

            if(prodDetail.getQuantity() <= 0 ){
                throw new Exception(new InvalidParameterException("Zero product Quantity not allowed"));
            }
        }

        addressDetailsService.addAddressDetails(request.getAddress());
        return orderService.makeCartEntryToOrders
                (
                        request.getCartId(),
                        request.getProducts(),
                        request.getAddress(),
                        request.getTotalCost()
                );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderConfirmation/")
    public OrderSummaryResponse confirmPaymentForOrder(@RequestBody PaymentRequest request) {

        return orderService.confirmOrderPaymentRequest
                (
                        request.getOrderId(),
                        request.getPaymentId(),
                        request.getDateOfPurchase(),
                        request.getModeOfPayment(),
                        request.getSuccess()
                );


    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary/{orderId}")
    public OrderSummaryResponse postOrderSummary(@PathVariable("orderId") UUID orderId){

        return orderService.createResponseForOrderSummary(orderId);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary/")
    public List<OrderSummaryResponse> postAllOrderSummary(){

        return orderService.createResponseForAllOrderSummary();
    }






}
