package micro_service_4.micro_service_4.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderProductMapService;
import micro_service_4.micro_service_4.Service.OrderService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;
    @Autowired
    private OrderProductMapService orderProductMapService ;


    @RequestMapping(method = RequestMethod.POST, value = "/addCartEntry")
    public ResponseEntity<CustomResponse> addCartEntry(@RequestBody CartRequest request) throws Exception {

        System.out.println("frontend se call aai");
        System.out.println(request.getAddress());
        //CartRequestResponse
        if(request.getProducts().size() == 0)
                return ResponseEntity.status(200).body(new CustomResponse(404,"No products Found",null));

        if(request.getTotalCost() == 0)
            return ResponseEntity.status(200).body(new CustomResponse(500,"Zero total Cost not allowed  ",null));

        if(request.getAddress().getPinCode() == null || request.getAddress().getPinCode().equals(""))
            return ResponseEntity.status(200).body(new CustomResponse(500,"Pin Code required",null));

        for(ProductDetails prodDetail: request.getProducts()){

            if(prodDetail.getQuantity() <= 0 ){
                return ResponseEntity.status(200).body(new CustomResponse(500,"Zero product Quantity not allowed",null));
            }
        }

        List<ProductDetails> validate_products= request.getProducts();
        List<String> requestToValidate = new ArrayList<>();

        for(ProductDetails prod: validate_products){
            requestToValidate.add(prod.getProductId());
        }

        RestTemplate restTemplate = new RestTemplate();

        final String uri ="https://bde8a250.ngrok.io/getProductsById";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List> entity = new HttpEntity<>(requestToValidate,headers);

        String productList = restTemplate.postForObject(uri,entity,String.class);

        JSONObject obj = new JSONObject(productList);
        JSONArray arr = obj.getJSONArray("responseData");


        ObjectMapper mapper = new ObjectMapper();
        int totalCost =0;
        for(int i=0;i<arr.length();i++){

            ProductDetails prod = mapper.readValue(arr.getJSONObject(i).toString(),ProductDetails.class);

            if(!prod.getProductId().equals(validate_products.get(i).getProductId()))
                return ResponseEntity.status(200).body(new CustomResponse(403,"Product Id doesn't match",null));
            if(prod.getQuantity()<validate_products.get(i).getQuantity())
                return ResponseEntity.status(200).body(new CustomResponse(403,"Quantity doesn't match",null));
            if(!prod.getPrice().equals(validate_products.get(i).getPrice()))
                return ResponseEntity.status(200).body(new CustomResponse(403,"Price has been changed",null));


            totalCost += validate_products.get(i).getPrice() * validate_products.get(i).getQuantity();
        }

        if(totalCost != request.getTotalCost()){
            return ResponseEntity.status(200).body(new CustomResponse(403,"Total cost is incorrect",null));
        }

        addressDetailsService.addAddressDetails(request.getAddress());
        CartRequestResponse cartRequestResponse = orderService.makeCartEntryToOrders
                (
                        request.getCartId(),
                        request.getProducts(),
                        request.getAddress(),
                        request.getTotalCost()
                );

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",cartRequestResponse));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderConfirmation")
    public ResponseEntity<CustomResponse> confirmPaymentForOrder(@RequestBody PaymentRequest request) {

        OrderSummaryResponse response = orderService.confirmOrderPaymentRequest
                (
                        request.getOrderId(),
                        request.getPaymentId(),
                        request.getDateOfPurchase(),
                        request.getModeOfPayment(),
                        request.getSuccess()
                );

        List<ProductDetails> productList = orderProductMapService.getAllProductsByOrderId(request.getOrderId());

        UpdateQuantity updateQuantity = new UpdateQuantity( productList,true);
        final String uri = "https://bde8a250.ngrok.io/updateQuantity";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateQuantity> entity = new HttpEntity<>(updateQuantity,headers);

        String resp = restTemplate.postForObject(uri,entity,String.class);

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",response));


    }


    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary/{orderId}")
    public ResponseEntity<CustomResponse> postOrderSummary(@PathVariable("orderId") UUID orderId){

        OrderSummaryResponse orderSummaryResponse = orderService.createResponseForOrderSummary(orderId);

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",orderSummaryResponse));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary")
    public ResponseEntity<CustomResponse> postAllOrderSummary(){
        List<OrderSummaryResponse> response =  orderService.createResponseForAllOrderSummary();

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",response));
    }


    @RequestMapping(method = RequestMethod.GET, value = "/cancelOrder/{orderId}")
    public ResponseEntity<CustomResponse> cancelOrderRequest(@PathVariable UUID orderId){
        orderService.cancelOrderRequest(orderId);

        final String cancelOrderRequest = "https://ff3b33d3.ngrok.io/payment/cancel/" + orderId;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(cancelOrderRequest,String.class);


        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",null));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/changeStatusToOFD/{orderId}")
    public ResponseEntity<CustomResponse> updateOrderStatusToOFD(@PathVariable UUID orderId)
    {
        orderService.updateOrderRequestToOFD(orderId);
        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",null));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/changeStatusToD/{orderId}")
    public ResponseEntity<CustomResponse> updateOrderStatusToD(@PathVariable UUID orderId)
    {
        orderService.updateOrderRequestToD(orderId);
        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",null));
    }


    @RequestMapping(method = RequestMethod.GET, value= "/orderDetails/{orderIdString}")
    public ResponseEntity<CustomResponse> getOrderDetailsForPayment(@PathVariable String orderIdString){

        UUID orderId;
        try{
            orderId = UUID.fromString(orderIdString);
        }catch (Exception e){
            return ResponseEntity.status(200).body(new CustomResponse(500,"Invalid Orderid",null));
        }

        PaymentOrderResponse paymentOrderResponse = orderService.getResponseForPaymentService(orderId);
        return ResponseEntity.status(200).body(new CustomResponse(200,"All Okay",paymentOrderResponse));


    }

}
