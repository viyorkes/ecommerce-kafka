package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of())) {
            service.run();
        }
    }

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Order> record)throws ExecutionException, InterruptedException {
        System.out.println("---------------------------------------");
        System.out.println("processing NewOrder");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //ignoring
            e.printStackTrace();
        }


       var order= record.value();

        if(order.getAmount().compareTo(new BigDecimal("4500"))>=0){
            System.out.println("Order is a fraud!!!!");
            //in this model fraude happens when amount is >4500
            orderDispatcher.send("ECOMMERCE_ORDER_REJECT", order.getUserId(),order);
        }else {
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserId(),order);
            System.out.println("your Order is aproved OK"+ order);

        }

        System.out.println("Order processed");

    }

    private boolean isFraud(Order order) {
    return order.getAmount().compareTo(new BigDecimal("4500"))>=0;
    }

}
