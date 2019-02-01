package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.ServiceablePinCode;
import micro_service_4.micro_service_4.Repository.OrderCartMapRepository;
import micro_service_4.micro_service_4.Repository.ServicablePinCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicablePinCodeService {

    @Autowired
    private ServicablePinCodeRepository servicablePinCodeRepository;


    public ServiceablePinCode findById(Integer id){

        return this.servicablePinCodeRepository.findById(id)
                .orElse(null);

    }

    public void addPinCode(ServiceablePinCode pinCode){
        this.servicablePinCodeRepository.save(pinCode);
    }
}
