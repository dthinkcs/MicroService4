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

    @RequestMapping(method = RequestMethod.POST, value = "/orderConfirmation")
    public OrderSummaryResponse confirmPaymentForOrder(@RequestBody PaymentRequest request) {

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

//        System.out.println(res.toString());

        return response;


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
