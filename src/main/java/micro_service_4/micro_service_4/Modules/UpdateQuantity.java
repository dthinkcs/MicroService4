package micro_service_4.micro_service_4.Modules;

import java.util.List;

public class UpdateQuantity {
private Boolean toReduce;
 private List<ProductDetails> productIds;


    public UpdateQuantity() {
    }

    public UpdateQuantity(Boolean flag, List<ProductDetails> productIds) {
        this.toReduce = flag;
        this.productIds = productIds;
    }

    public Boolean getToReduce() {
        return toReduce;
    }

    public List<ProductDetails> getProductIds() {
        return productIds;
    }

    public void setToReduce(Boolean toReduce) {
        this.toReduce = toReduce;
    }

    public void setProductIds(List<ProductDetails> productIds) {
        this.productIds = productIds;
    }
}





