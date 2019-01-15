package micro_service_4.micro_service_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductMapService {
    @Autowired
    private OrderMapRepository ordermaprepository;
    public void add_maporder(OrderProductMap orderproductmap){
        System.out.println("mai aaya");
        ordermaprepository.save(orderproductmap);
    }
}
