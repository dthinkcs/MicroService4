package micro_service_4.micro_service_4.Service;

import micro_service_4.micro_service_4.Modules.AddressDetails;
import micro_service_4.micro_service_4.Repository.AddressDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressDetailsService {
    @Autowired
    private AddressDetailsRepository addressdetailsrepository;
    private void addAddressDetailsEntry(AddressDetails addressdetails){
        addressdetailsrepository.save(addressdetails);
    }

    AddressDetails getAddressDetails(UUID addressId){
        Optional<AddressDetails> addressDetails = addressdetailsrepository.findById(addressId);
        return addressDetails.get();
    }

    public void addAddressDetails(AddressDetails addressDetails){
        addressDetails.setAddressId(UUID.randomUUID());
        this.addAddressDetailsEntry(addressDetails);
    }




}

