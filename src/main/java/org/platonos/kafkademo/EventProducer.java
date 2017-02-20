package org.platonos.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@ApplicationScoped
public class EventProducer {

    private Producer<String, CoffeeEvent> producer;

    //@Inject
    Properties kafkaProperties;

    //@Inject
    Logger logger = LoggerProducer.getLogger(getClass());

    @PostConstruct
    private void init() throws UnknownHostException {
        kafkaProperties = new Properties();
        kafkaProperties.put("client.id", InetAddress.getLocalHost().getHostName());
        kafkaProperties.put("bootstrap.servers", "host1:9092,host2:9092");
        kafkaProperties.put("acks", "all");

        producer = new KafkaProducer<>(kafkaProperties);
    }

    public Future<RecordMetadata> publish(CoffeeEvent event) {
        final ProducerRecord<String, CoffeeEvent> record = new ProducerRecord<>("test", event);
        logger.info("publishing = " + record);
        Future<RecordMetadata> result =  producer.send(record);
        logger.info("published = " + result);
        return result;
        //producer.flush();
        //logger.info("flused = " + record);
    }

    @PreDestroy
    public void close() {
        producer.close();
    }

}