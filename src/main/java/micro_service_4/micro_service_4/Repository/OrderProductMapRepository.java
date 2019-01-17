package micro_service_4.micro_service_4.Repository;

import micro_service_4.micro_service_4.Modules.OrderProductMap;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductMapRepository extends CrudRepository<OrderProductMap,UUID> {


    List<OrderProductMap> findAllProductsByOrderId(UUID orderId);
}
