package micro_service_4.micro_service_4;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.filter.FilteringParserDelegate;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import org.json.*;

@RestController
public class OrderController {
    @Autowired
 private OrderService orderservice;
    @Autowired
    private OrderProductMapService orderproductmap;

   @RequestMapping(method = RequestMethod.POST, value = "/OrderDetails")
     public Response getOrderDetails(@RequestBody Map<String, String> request_body) throws Exception{

       Response response = new Response();
       System.out.println("hello");
        final String uri = "https://demo0655277.mockable.io/";
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> payment_api_response = restTemplate.getForObject(uri, HashMap.class);

        UUID cart_id = UUID.fromString(payment_api_response.get("cart_id"));
        String address = payment_api_response.get("address");
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date_of_purchase = date_formatter.parse(payment_api_response.get("date"));
        Integer total_cost = Integer.parseInt(payment_api_response.get("total_cost"));

        UUID order_id = saveToOrderTable(date_of_purchase,address,total_cost);
        response.setAddress(address);
        response.setDate_of_purchase(date_of_purchase);
        response.setOrder_id(order_id);

        final String uri2 = "http://demo4021689.mockable.io/";

      String cart_api_response = restTemplate.getForObject(uri2, String.class);
      JSONObject arr1 = new JSONObject(cart_api_response);

       JSONArray arr = (JSONArray)arr1.get("products");
        List<UUID> product_id_list = new ArrayList<>();
       for (int i = 0; i < arr.length(); i++)
       {
           UUID curr_prod_id = UUID.fromString(arr.getJSONObject(i).getString("prod_id"));
           product_id_list.add(curr_prod_id);
          Integer curr_qty = arr.getJSONObject(i).getInt("qty");
           saveToOrderProductMap(order_id,curr_prod_id,curr_qty);
       }

       response.setProducts(product_id_list);

        return response;
    }

    private void saveToOrderProductMap(UUID order_id, UUID curr_prod_id, Integer curr_qty) {

       OrderProductMap op_map = new OrderProductMap(order_id,curr_prod_id,curr_qty);
       orderproductmap.add_maporder(op_map);
    }

    private UUID saveToOrderTable(Date date_of_purchase, String address, Integer total_cost){

       UUID order_id =UUID.randomUUID();
       Order order = new Order(order_id,date_of_purchase,address,total_cost);
        System.out.println(order.getOrder_id());
        System.out.println(order.getDate_of_purchase());
        System.out.println(orderservice);
       orderservice.add_order(order);

       return order_id;

    }
}
