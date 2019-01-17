package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.OrderProductMap;
import micro_service_4.micro_service_4.Modules.ProductDetails;
import micro_service_4.micro_service_4.Repository.OrderProductMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderProductMapService {
    @Autowired
    private OrderProductMapRepository ordermaprepository;

    public void add_maporder(OrderProductMap orderproductmap) {
        ordermaprepository.save(orderproductmap);
    }


    public List<ProductDetails> getAllProductsByOrderId(UUID orderId) {

        List<OrderProductMap> allOrderProducts = ordermaprepository.findAllProductsByOrderId(orderId);

        List<ProductDetails> allProducts = new ArrayList<>();


        for (OrderProductMap orderProduct : allOrderProducts) {

            ProductDetails productDetails = new ProductDetails
                    (
                            orderProduct.getProductId(),
                            orderProduct.getProductName(),
                            "",
                            orderProduct.getPrice(),
                            orderProduct.getQuantity()
                    );

            allProducts.add(productDetails);

        }

        return allProducts;

    }
}
