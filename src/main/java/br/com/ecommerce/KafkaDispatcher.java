package br.com.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher  implements Closeable {


    private final KafkaProducer<String, String> producer;

    KafkaDispatcher(){
        this.producer = new KafkaProducer<String,String>(properties());

    }


    public void send (String topic,String key, String value) throws ExecutionException, InterruptedException{
        var record = new ProducerRecord<>(topic, key, value);
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("---SUCCESS---" +
                               "---TOPIC---" + data.topic() +
                               "---PARTITION---" + data.partition() +
                               "---TIMESTEMP---" + data.timestamp());
        };

        producer.send(record,callback).get();

    }

    @Override
    public void close()  {
        producer.close();
    }

    public static Properties properties() {

        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;

    }


}