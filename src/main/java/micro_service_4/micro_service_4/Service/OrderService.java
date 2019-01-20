package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Exceptions.OrderNotFoundException;
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
    private OrderRepository orderRepository;

    @Autowired
    private AddressDetailsService addressDetailsService;


    public CartRequestResponse makeCartEntryToOrders(String cartId, List<ProductDetails> productDetails, AddressDetails address, Integer totalCost) {

        UUID orderId =  saveToOrderTable(null, address.getAddressId(), totalCost);

        for (ProductDetails prod : productDetails) {
            orderProductMapService.saveToOrderProductMap(orderId, prod.getProductId(), prod.getProductName(), prod.getQuantity(), prod.getPrice());
        }

        return new CartRequestResponse(orderId);
    }

    public OrderSummaryResponse confirmOrderPaymentRequest(UUID orderId, String paymentId, Date dateOfPurchase, String modeOfPayment, Boolean isSuccess) {

        this.confirmOrderPayment(orderId,dateOfPurchase,paymentId);
        return createResponseForOrderSummary(orderId);
    }


    public OrderSummaryResponse createResponseForOrderSummary(UUID orderId){

        Order order = orderRepository.findById(orderId)
                        .orElseThrow(()->new OrderNotFoundException(orderId));

        OrderSummaryResponse response = new OrderSummaryResponse();
        response.setOrder_id(order.getOrderId());
        response.setDate_of_purchase(order.getDateOfPurchase());
        response.setAddress(addressDetailsService.getAddressDetails(order.getAddressId()));
        response.setProducts(orderProductMapService.getAllProductsByOrderId(order.getOrderId()));
        response.setOrderConfirmed(order.isConfirmed());
        response.setPayment_id(order.getPaymentId());
        return response;
    }


    public List<OrderSummaryResponse> createResponseForAllOrderSummary(){

        Iterable<Order> allOrdersIterable = orderRepository.findAll();
        List<OrderSummaryResponse> response = new ArrayList<>();

        for(Order order:allOrdersIterable){
            response.add(createResponseForOrderSummary(order.getOrderId()));
        }

        return response;
    }



    private UUID saveToOrderTable(Date date_of_purchase, UUID address, Integer total_cost) {

        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, date_of_purchase, address, total_cost, false, null);
        System.out.println(order.getOrderId());
        System.out.println(order.getDateOfPurchase());
        System.out.println(this);
        this.addOrder(order);

        return orderId;

    }


    private void addOrder(Order order) {
        orderRepository.save(order);
    }

    private void confirmOrderPayment(UUID orderId,Date dateOfPurchase, String paymentId) {

        orderRepository.findById(orderId)
                .map(order -> {
                    order.setConfirmed(true);
                    order.setDateOfPurchase(dateOfPurchase);
                    order.setPaymentId(paymentId);
                    return orderRepository.save(order);
                }).orElseThrow(()->new OrderNotFoundException(orderId));

    }


}
