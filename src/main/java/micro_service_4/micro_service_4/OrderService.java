package micro_service_4.micro_service_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderrepository;
    public void add_order(Order order){
        System.out.println("mai aaya");
        orderrepository.save(order);
    }
}
