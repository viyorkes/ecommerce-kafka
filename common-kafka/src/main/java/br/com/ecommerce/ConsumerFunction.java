package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public interface ConsumerFunction<T> {


    void consumer (ConsumerRecord<String, T>record) throws ExecutionException, InterruptedException, SQLException, IOException;
}
