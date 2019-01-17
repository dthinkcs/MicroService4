package micro_service_4.micro_service_4;

import micro_service_4.micro_service_4.Modules.AddressDetails;
import micro_service_4.micro_service_4.Modules.ProductDetails;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Response {

    private UUID order_id;
    //product
    private List<ProductDetails> products;

    private Date date_of_purchase;
    private AddressDetails address;

    private UUID payment_id;

    public Response(){

    }

    public Response(UUID order_id, List<ProductDetails> products, Date date_of_purchase, AddressDetails address, UUID payment_id) {
        this.order_id = order_id;
        this.products = products;
        this.date_of_purchase = date_of_purchase;
        this.address = address;
        this.payment_id = payment_id;
    }

    public UUID getOrder_id() {
        return order_id;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public List<ProductDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetails> products) {
        this.products = products;
    }

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(Date date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public AddressDetails getAddress() {
        return address;
    }

    public void setAddress(AddressDetails address) {
        this.address = address;
    }

    public UUID getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(UUID payment_id) {
        this.payment_id = payment_id;
    }



}
