package br.com.ecommerce;


import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var dispatcher = new KafkaDispatcher()) {

            for (var i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();

                var value = key + "123,1233,177";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "WE ARE PROCESSING YOUR ORDER";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }

        }
    }

}