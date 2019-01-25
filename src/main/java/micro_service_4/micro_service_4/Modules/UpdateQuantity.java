package micro_service_4.micro_service_4.Modules;

import java.util.List;

public class UpdateQuantity {
private Boolean toReduce;
 private List<IndividualProductQuantity> productIds;


    public UpdateQuantity() {
    }

    public UpdateQuantity(Boolean toReduce, List<IndividualProductQuantity> productIds) {
        this.toReduce = toReduce;
        this.productIds = productIds;
    }

    public Boolean getToReduce() {
        return toReduce;
    }

    public List<IndividualProductQuantity> getProductIds() {
        return productIds;
    }

    public void setToReduce(Boolean toReduce) {
        this.toReduce = toReduce;
    }

    public void setProductIds(List<IndividualProductQuantity> productIds) {
        this.productIds = productIds;
    }
}





