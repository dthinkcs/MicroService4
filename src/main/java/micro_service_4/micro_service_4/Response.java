package micro_service_4.micro_service_4;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Response {

    private UUID order_id;
    //product
    private List<UUID> products;

    private Date date_of_purchase;
    private String address;

    private UUID payment_id;

    public Response(){

    }

    public Response(UUID order_id, List<UUID> products, Date date_of_purchase, String address, UUID payment_id) {
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

    public List<UUID> getProducts() {
        return products;
    }

    public void setProducts(List<UUID> products) {
        this.products = products;
    }

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(Date date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(UUID payment_id) {
        this.payment_id = payment_id;
    }



}
