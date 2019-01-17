package micro_service_4.micro_service_4.Repository;

import micro_service_4.micro_service_4.Modules.CartOrderMap;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartOrderMapRepository extends CrudRepository<CartOrderMap, UUID> {


    CartOrderMap findByCartId(UUID cartId);

}
