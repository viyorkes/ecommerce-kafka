package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.Properties;
import java.util.UUID;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class)) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
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

        System.out.println("Order processed");

    }

}
