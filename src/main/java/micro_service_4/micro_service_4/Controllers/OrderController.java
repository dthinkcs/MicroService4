package micro_service_4.micro_service_4.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderProductMapService;
import micro_service_4.micro_service_4.Service.OrderService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.util.*;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressDetailsService addressDetailsService;
    @Autowired
    private OrderProductMapService orderProductMapService ;


    @RequestMapping(method = RequestMethod.POST, value = "/addCartEntry/")
    public ResponseEntity<CustomResponse> addCartEntry(@RequestBody CartRequest request) throws Exception {

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

//        ResponseEntity<Object> response = restTemplate.exchange(
//                "http://demo0655277.mockable.io/",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ProductDetails>>(){});
//        System.out.print();
//        List<ProductDetails> productList = response.getBody();

        List<ProductDetails> validate_products= request.getProducts();
        RestTemplate restTemplate = new RestTemplate();
        String productList = restTemplate.getForObject("http://demo3541090.mockable.io/",String.class);
        System.out.println(productList);
        JSONObject obj = new JSONObject(productList);
        JSONArray arr =obj.getJSONArray("products");
        ObjectMapper mapper = new ObjectMapper();
        for(int i=0;i<arr.length();i++){

            ProductDetails prod = mapper.readValue(arr.getJSONObject(i).toString(),ProductDetails.class);

            if(!prod.getProductId().equals(validate_products.get(i).getProductId()))
                return ResponseEntity.status(200).body(new CustomResponse(403,"Product Id doesn't match",null));
            if(!prod.getQuantity().equals(validate_products.get(i).getQuantity()))
                return ResponseEntity.status(200).body(new CustomResponse(403,"Quantity doesn't match",null));
            if(!prod.getPrice().equals(validate_products.get(i).getPrice()))
                return ResponseEntity.status(200).body(new CustomResponse(403,"Price has been changed",null));

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
        final String uri = "http://gourav9.localhost.run/updateQuantity";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("yha aakr ruka");
        System.out.println(updateQuantity.getProductIds());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateQuantity> entity = new HttpEntity<>(updateQuantity,headers);

        String resp = restTemplate.postForObject(uri,entity,String.class);
        System.out.println(resp);
        System.out.println("hi");

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",response));


    }


    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary/{orderId}")
    public ResponseEntity<CustomResponse> postOrderSummary(@PathVariable("orderId") UUID orderId){

        OrderSummaryResponse orderSummaryResponse =  orderService.createResponseForOrderSummary(orderId);

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",orderSummaryResponse));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderSummary/")
    public ResponseEntity<CustomResponse> postAllOrderSummary(){
        List<OrderSummaryResponse> response =  orderService.createResponseForAllOrderSummary();

        return ResponseEntity.status(200).body(new CustomResponse(200,"All okay",response));
    }




    @RequestMapping(method = RequestMethod.GET, value = "/cancelOrder/{orderId}")
    public ResponseEntity<CustomResponse> cancelOrderRequest(@PathVariable UUID orderId){
        orderService.cancelOrderRequest(orderId);
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







}
