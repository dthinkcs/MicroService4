package micro_service_4.micro_service_4.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderProductMapService;
import micro_service_4.micro_service_4.Service.OrderService;

import org.apache.coyote.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.DataInput;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;
    @Autowired
    private OrderProductMapService orderProductMapService ;


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

//        ResponseEntity<Object> response = restTemplate.exchange(
//                "http://demo0655277.mockable.io/",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ProductDetails>>(){});
//        System.out.print();
//        List<ProductDetails> productList = response.getBody();

        List<ProductDetails> validate_products= request.getProducts();
        RestTemplate restTemplate = new RestTemplate();
        String productList = restTemplate.getForObject("http://demo0655277.mockable.io/",String.class);
        System.out.println(productList);
        JSONObject obj = new JSONObject(productList);
        JSONArray arr =obj.getJSONArray("products");
        ObjectMapper mapper = new ObjectMapper();
        for(int i=0;i<arr.length();i++){

            ProductDetails prod = mapper.readValue(arr.getJSONObject(i).toString(),ProductDetails.class);

            /*System.out.println(prod.getProductId());
            System.out.println(validate_products.get(i).getProductId());*/
            if(!prod.getProductId().equals(validate_products.get(i).getProductId()))
                throw new Exception(new InvalidParameterException("Product Id doesn't match"));
            if(!prod.getQuantity().equals(validate_products.get(i).getQuantity()))
                throw new Exception(new InvalidParameterException("Quantity doesn't match"));
            if(!prod.getPrice().equals(validate_products.get(i).getPrice()))
                throw new Exception(new InvalidParameterException("Price has been changed"));

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

       /* List<ProductDetails> productList = orderProductMapService.getAllProductsByOrderId(request.getOrderId());
        UpdateQuantity quantity = new UpdateQuantity(true,productList);
        final String uri = "http://gourav1.localhost.run/updateQuantity";
        RestTemplate restTemplate = new RestTemplate();
        UpdateQuantity result = restTemplate.postForObject( uri, quantity, UpdateQuantity.class);

        System.out.println(result);
*/
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


    @RequestMapping(method = RequestMethod.GET, value = "/cancelOrder/{orderId}")
    public void cancelOrderRequest(@PathVariable UUID orderId){
        orderService.cancelOrderRequest(orderId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/changeStatusToOFD/{orderId}")
    public void updateOrderStatusToOFD(@PathVariable UUID orderId){
        orderService.updateOrderRequestToOFD(orderId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/changeStatusToD/{orderId}")
    public void updateOrderStatusToD(@PathVariable UUID orderId){
        orderService.updateOrderRequestToD(orderId);
    }



}
