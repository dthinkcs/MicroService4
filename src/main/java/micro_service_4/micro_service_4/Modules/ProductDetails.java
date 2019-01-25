package micro_service_4.micro_service_4.Modules;

import java.util.UUID;

public class ProductDetails {


    private String productId;
    private String productName;
    private String brand;
    private Integer price;
    private Integer quantity;

    public ProductDetails(){

    }

    public ProductDetails(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    public ProductDetails(String productId,String productName, String brand, Integer price, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "[" + this.productId + "," + this.brand + "," + this.price + "," + this.quantity + "]";
    }
}