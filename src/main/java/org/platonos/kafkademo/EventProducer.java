package org.platonos.kafkademo;

import org.apache.kafka.clients.producer.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@ApplicationScoped
public class EventProducer {

    private Producer<String, CoffeeEvent> producer;

    @Inject
    private Properties kafkaProperties;

    //@Inject
    private Logger logger = LoggerProducer.getLogger(getClass());

    @PostConstruct
    private void init() {
        producer = new KafkaProducer<>(kafkaProperties);
    }

    public Future<RecordMetadata> publish(final CoffeeEvent event) {
        final ProducerRecord<String, CoffeeEvent> record = new ProducerRecord<>("test", event);
        Future<RecordMetadata> result =  producer.send(record);
        producer.flush();
        return result;
    }

    @PreDestroy
    public void close() {
        producer.close();
    }

}