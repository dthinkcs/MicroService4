package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Repository.AddressDetailsRepository;
import micro_service_4.micro_service_4.Repository.OrderRepository;
import micro_service_4.micro_service_4.Response;
import micro_service_4.micro_service_4.Service.OrderProductMapService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderProductMapService orderproductmap;

    @Autowired
    private CartOrderMapService cartOrderMapService;

    @Autowired
    private OrderRepository orderrepository;

    @Autowired
    private AddressDetailsService addressDetailsService;


    public void foo(UUID cartId, List<ProductDetails> productDetails, AddressDetails address, Integer totalCost) {


        UUID orderId = saveToOrderTable(null, address.getAddressId(), totalCost);

        for (ProductDetails prod : productDetails) {
            saveToOrderProductMap(orderId, prod.getProductId(), prod.getProductName(), prod.getQuantity(), prod.getPrice());
        }
        System.out.println(cartId);
        System.out.println(orderId);
        CartOrderMap cpMap = new CartOrderMap(orderId, cartId);
        cartOrderMapService.add_mapcartorder(cpMap);

    }

    public Response bar(UUID cartId, UUID paymentId, Date dateOfPurchase, String modeOfPayment, Boolean isSuccess) {

//        if (!isSuccess)
//            return null;

        UUID orderId = cartOrderMapService.getOrderIdFromCartId(cartId);
        Order order = this.confirmOrderPayment(orderId,dateOfPurchase);
        cartOrderMapService.removeEntryForOrderId(orderId);

        Response response =  createResponseForOrderSummary(order);

        final String uri = "https://www.mocky.io/v2/5185415ba171ea3a00704eed";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject( uri, response.getProducts(), List.class);

        return response;
    }


    private Response createResponseForOrderSummary(Order order){

        Response response = new Response();
        response.setOrder_id(order.getOrderId());
        response.setDate_of_purchase(order.getDateOfPurchase());
        response.setAddress(addressDetailsService.getAddressDetails(order.getAddressId()));
        response.setProducts(orderproductmap.getAllProductsByOrderId(order.getOrderId()));
        return response;
    }

    private void saveToOrderProductMap(UUID order_id, UUID curr_prod_id, String productName, Integer curr_qty, Integer price) {

        OrderProductMap op_map = new OrderProductMap(order_id, curr_prod_id, productName, curr_qty, price);
        System.out.println(op_map);
        orderproductmap.add_maporder(op_map);
    }

    private UUID saveToOrderTable(Date date_of_purchase, UUID address, Integer total_cost) {

        UUID order_id = UUID.randomUUID();
        Order order = new Order(order_id, date_of_purchase, address, total_cost, false);
        System.out.println(order.getOrderId());
        System.out.println(order.getDateOfPurchase());
        System.out.println(this);
        this.add_order(order);

        return order_id;

    }


    public void add_order(Order order) {
        orderrepository.save(order);
    }

    public Order confirmOrderPayment(UUID orderId,Date dateOfPurchase) {

        System.out.println(dateOfPurchase);

        System.out.println(orderId);

        return orderrepository.findById(orderId)
                .map(order -> {
                    order.setConfirmed(true);
                    order.setDateOfPurchase(dateOfPurchase);
                    return orderrepository.save(order);
                }).get();

    }
}
