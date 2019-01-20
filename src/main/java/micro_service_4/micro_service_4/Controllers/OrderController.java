package micro_service_4.micro_service_4.Controllers;

import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;


    @RequestMapping(method = RequestMethod.POST, value = "/addCartEntry/")
    public CartRequestResponse addCartEntry(@RequestBody CartRequest request) throws Exception {

        addressDetailsService.addAddressDetails(request.getAddress());
        return orderService.foo
                (
                        request.getCartId(),
                        request.getProducts(),
                        request.getAddress(),
                        request.getTotalCost()
                );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderConfirmation/")
    public OrderSummaryResponse confirmPaymentForOrder(@RequestBody PaymentRequest request) {

        return orderService.bar
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



}
