package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.*;
import micro_service_4.micro_service_4.Repository.OrderRepository;
import micro_service_4.micro_service_4.Modules.OrderSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderProductMapService orderProductMapService;

    @Autowired
    private CartOrderMapService cartOrderMapService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressDetailsService addressDetailsService;


    public CartRequestResponse foo(UUID cartId, List<ProductDetails> productDetails, AddressDetails address, Integer totalCost) {

        UUID orderId = saveToOrderTable(null, address.getAddressId(), totalCost);

        for (ProductDetails prod : productDetails) {
            orderProductMapService.saveToOrderProductMap(orderId, prod.getProductId(), prod.getProductName(), prod.getQuantity(), prod.getPrice());
        }

        CartOrderMap cpMap = new CartOrderMap(orderId, cartId);
        cartOrderMapService.add_mapcartorder(cpMap);

        return new CartRequestResponse(orderId);
    }

    public OrderSummaryResponse bar(UUID orderId, UUID paymentId, Date dateOfPurchase, String modeOfPayment, Boolean isSuccess) {

//        if (!isSuccess)
//            return null;

        this.confirmOrderPayment(orderId,dateOfPurchase);
        OrderSummaryResponse response =  createResponseForOrderSummary(orderId);

        //TODO
        //make call to catalog service for updating inventory
//        final String uri = "https://www.mocky.io/v2/5185415ba171ea3a00704eed";
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForObject( uri, response.getProducts(), List.class);

        return response;
    }


    public OrderSummaryResponse createResponseForOrderSummary(UUID orderId){

        Order order = orderRepository.findById(orderId).get();
        OrderSummaryResponse response = new OrderSummaryResponse();
        response.setOrder_id(order.getOrderId());
        response.setDate_of_purchase(order.getDateOfPurchase());
        response.setAddress(addressDetailsService.getAddressDetails(order.getAddressId()));
        response.setProducts(orderProductMapService.getAllProductsByOrderId(order.getOrderId()));
        response.setOrderConfirmed(order.isConfirmed());
        return response;
    }



    private UUID saveToOrderTable(Date date_of_purchase, UUID address, Integer total_cost) {

        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, date_of_purchase, address, total_cost, false);
        System.out.println(order.getOrderId());
        System.out.println(order.getDateOfPurchase());
        System.out.println(this);
        this.addOrder(order);

        return orderId;

    }


    private void addOrder(Order order) {
        orderRepository.save(order);
    }

    private void confirmOrderPayment(UUID orderId,Date dateOfPurchase) {

        orderRepository.findById(orderId)
                .map(order -> {
                    order.setConfirmed(true);
                    order.setDateOfPurchase(dateOfPurchase);
                    return orderRepository.save(order);
                });

    }
}
