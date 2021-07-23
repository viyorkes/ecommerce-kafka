package br.com.ecommerce;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var orderDispatcher = new KafkaDispatcher<Order>()) {
            try(var emailDispatcher = new KafkaDispatcher<String>()) {
                for (var i = 0; i < 10; i++) {
                    var key = UUID.randomUUID().toString();

                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);


                    var order = new Order(userId, orderId, amount);

                    var value = key + "123,1233,177";
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", key, order);

                    var email = "WE ARE PROCESSING YOUR ORDER";
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
                }
            }
        }
    }

}