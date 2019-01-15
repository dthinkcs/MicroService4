package micro_service_4.micro_service_4;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderMapRepository extends CrudRepository<OrderProductMap,UUID> {
}
