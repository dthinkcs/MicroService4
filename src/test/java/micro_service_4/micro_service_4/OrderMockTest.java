package micro_service_4.micro_service_4;

import micro_service_4.micro_service_4.Controllers.OrderController;
import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Repository.OrderCartMapRepository;
import micro_service_4.micro_service_4.Repository.OrderRepository;
import micro_service_4.micro_service_4.Service.AddressDetailsService;
import micro_service_4.micro_service_4.Service.OrderProductMapService;
import micro_service_4.micro_service_4.Service.OrderService;
import micro_service_4.micro_service_4.Service.ServicablePinCodeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.invocation.MatchersBinder;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OrderMockTest {


    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderProductMapService orderProductMapService;

    @Mock
    private AddressDetailsService addressDetailsService;


    @Mock
    private ServicablePinCodeService servicablePinCodeService;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        orderController = new OrderController(orderService,addressDetailsService,orderProductMapService,servicablePinCodeService,restTemplate);


    }


    @Test
    public void addCartEntryTestWithNoProducts() throws Exception{

        UUID baseUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Date currDate = new Date();
        CartRequest cartRequest = new CartRequest();
        cartRequest.setCartId("1234");
        cartRequest.setTotalCost(10);

        AddressDetails addressDetails = new AddressDetails(baseUuid,"a","a","a","a",111111);

        cartRequest.setAddress(addressDetails);

        List<ProductDetails> productDetails = new ArrayList<>();
        cartRequest.setProducts(productDetails);

        ResponseEntity<CustomResponse> resp = orderController.addCartEntry(cartRequest);
        CustomResponse<OrderSummaryResponse> customResponse = resp.getBody();
        OrderSummaryResponse returnedResponse = customResponse.getResponseData();
        int returnedStatus = customResponse.getStatusCode();

        Assert.assertEquals(404,returnedStatus);
        Assert.assertNull(returnedResponse);

        // ---------------------------------------------------------------------------

        ProductDetails pd = new ProductDetails("100","A","a",1,1);
        productDetails.add(pd);

        cartRequest.setProducts(productDetails);

        resp = orderController.addCartEntry(cartRequest);
        customResponse = resp.getBody();
        returnedResponse = customResponse.getResponseData();
        returnedStatus = customResponse.getStatusCode();

        Assert.assertEquals(404,returnedStatus);
        Assert.assertNull(returnedResponse);


//        // ---------------------------------------------------------------------------
//
//        productDetails = new ArrayList<>();
//        pd = new ProductDetails("100","A","a",1,3);
//
//        productDetails.add(pd);
//
//        cartRequest.setProducts(productDetails);
//
//        given(servicablePinCodeService.findById(cartRequest.getAddress().getPinCode())).willReturn(null);
//
//
//        resp = orderController.addCartEntry(cartRequest);
//        customResponse = resp.getBody();
//        returnedResponse = customResponse.getResponseData();
//        returnedStatus = customResponse.getStatusCode();
//
//        Assert.assertEquals(404,returnedStatus);
//        Assert.assertNull(returnedResponse);

        // ---------------------------------------------------------------------------

        productDetails = new ArrayList<>();
        pd = new ProductDetails("100","A","a",1,3);
        productDetails.add(pd);

        cartRequest.setProducts(productDetails);


        String postCallResponse = "{\n" +
                "   \"statusCode\": 200,\n" +
                "   \"message\": \"ok\",\n" +
                "   \"responseData\": [\n" +
                "       {\n" +
                "           \"category\": \"A\",\n" +
                "           \"categoryId\": \"12\",\n" +
                "           \"productName\": \"Honor7A\",\n" +
                "           \"productId\": \"100\",\n" +
                "           \"price\": 1,\n" +
                "           \"quantity\": 3\n" +
                "       },\n" +
                "   ]\n" +
                "}";

//        RestTemplate restTemplate = new RestTemplate();
        given(servicablePinCodeService.findById(cartRequest.getAddress().getPinCode())).willReturn(new ServiceablePinCode(1100,true));

//        Mockito.when(restTemplate.postForObject(uri,entity,String.class)).thenReturn(postCallResponse);


        final String uri ="http://10.10.212.75:8080/getProductsById/";
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List> entity = new HttpEntity<>(new ArrayList(),headers);

        given(restTemplate.postForObject(uri,entity, String.class)).willReturn(postCallResponse);



        System.out.println("ab dekho");
        resp = orderController.addCartEntry(cartRequest);
        customResponse = resp.getBody();
        returnedResponse = customResponse.getResponseData();
        returnedStatus = customResponse.getStatusCode();

        Assert.assertEquals(404,returnedStatus);
        Assert.assertNull(returnedResponse);

        //-----------------------------------------------------------------------------------------------


        productDetails = new ArrayList<>();
        pd = new ProductDetails("100","A","a",1,3);

        productDetails.add(pd);

        cartRequest.setProducts(productDetails);


            postCallResponse = "{\n" +
                "   \"statusCode\": 200,\n" +
                "   \"message\": \"ok\",\n" +
                "   \"responseData\": [\n" +
                "       {\n" +
                "           \"category\": \"A\",\n" +
                "           \"categoryId\": \"12\",\n" +
                "           \"productName\": \"Honor7A\",\n" +
                "           \"productId\": \"100\",\n" +
                "           \"price\": 1,\n" +
                "           \"quantity\": 1\n" +
                "       },\n" +
                "   ]\n" +
                "}";

//        RestTemplate restTemplate = new RestTemplate();


        given(servicablePinCodeService.findById(cartRequest.getAddress().getPinCode())).willReturn(new ServiceablePinCode(1100,true));
        Mockito.when(restTemplate.postForObject("",null,String.class)).thenReturn(postCallResponse);

        resp = orderController.addCartEntry(cartRequest);
        customResponse = resp.getBody();
        returnedResponse = customResponse.getResponseData();
        returnedStatus = customResponse.getStatusCode();

        Assert.assertEquals(404,returnedStatus);
        Assert.assertNull(returnedResponse);

        //-----------------------------------------------------------------------------------------------

        productDetails = new ArrayList<>();
        pd = new ProductDetails("100","A","a",1,3);
        productDetails.add(pd);

        cartRequest.setProducts(productDetails);


        postCallResponse = "{\n" +
                "   \"statusCode\": 200,\n" +
                "   \"message\": \"ok\",\n" +
                "   \"responseData\": [\n" +
                "       {\n" +
                "           \"category\": \"A\",\n" +
                "           \"categoryId\": \"12\",\n" +
                "           \"productName\": \"Honor7A\",\n" +
                "           \"productId\": \"100\",\n" +
                "           \"price\": 1,\n" +
                "           \"quantity\": 7\n" +
                "       },\n" +
                "   ]\n" +
                "}";

//        RestTemplate restTemplate = new RestTemplate();
        given(servicablePinCodeService.findById(cartRequest.getAddress().getPinCode())).willReturn(new ServiceablePinCode(1100,true));
        Mockito.when(restTemplate.postForObject("",null,String.class)).thenReturn(postCallResponse);

        resp = orderController.addCartEntry(cartRequest);
        customResponse = resp.getBody();
        returnedResponse = customResponse.getResponseData();
        returnedStatus = customResponse.getStatusCode();

        System.out.println(customResponse.getMessage());
        Assert.assertEquals(200,returnedStatus);
//        Assert.assertNull(returnedResponse);


    }

    @Test
    public void addCartEntryTestWithIncorrectPinCode() throws Exception{

    }

    @Test
    public void addCartEntryTestWithProductQuantityZero() throws Exception{

    }

    @Test
    public void addCartEntryTestWithPriceOfProdChanged() throws Exception{

    }

    @Test
    public void addCartEntryTestWithQuantityOfProdChanged() throws Exception{

    }

    @Test
    public void addCartEntryTestWithTotalCostIncorrect() throws Exception{

    }

    @Test
    public void addCartEntryTestValid() throws Exception{

    }


    @Test
    public void getOrderSummaryTest() throws Exception{

        OrderSummaryResponse response = new OrderSummaryResponse();
        UUID baseUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Date currDate = new Date();
        AddressDetails addressDetails = new AddressDetails(baseUuid,"a","a","a","a",111111);
        List<ProductDetails> productDetails = new ArrayList<>();
        ProductDetails pd = new ProductDetails("100","A","a",1,1);
        productDetails.add(pd);

        response.setOrder_id(baseUuid);
        response.setDate_of_purchase(currDate);
        response.setAddress(addressDetails);
        response.setProducts(productDetails);
        response.setStatus(Order.Status.Confirmed);
        response.setPayment_id("123456");

        given(orderService.createResponseForOrderSummary(baseUuid)).willReturn(response);

        ResponseEntity<CustomResponse> resp = orderController.postOrderSummary(baseUuid);
        CustomResponse<OrderSummaryResponse> customResponse = resp.getBody();
        OrderSummaryResponse expected = customResponse.getResponseData();

        Assert.assertEquals(expected,response);


    }


}
