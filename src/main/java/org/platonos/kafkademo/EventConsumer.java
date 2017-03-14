package org.platonos.kafkademo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

/**
 * Created by Evert on 23-2-2017.
 */
public class EventConsumer implements Runnable {

    private final KafkaConsumer<String, CoffeeEvent> consumer;
    private final Consumer<CoffeeEvent> eventConsumer;
    private final AtomicBoolean closed = new AtomicBoolean();

    private Logger logger = Logger.getLogger(getClass().getName());

    EventConsumer(final Properties kafkaProperties,
                         final Consumer<CoffeeEvent> eventConsumer,
                         final String... topics) {
        this.eventConsumer = eventConsumer;
        consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(asList(topics));
    }

    @Override
    public void run() {
        logger.info("run");
        try {
            while (!closed.get()) {
                consume();
            }
        } catch (final WakeupException e) {
            // will wakeup for closing
            logger.info(e.getMessage());
        } finally {
            consumer.close();
        }
    }

    private void consume() {
        logger.info("consume");
        final ConsumerRecords<String, CoffeeEvent> records = consumer.poll(Long.MAX_VALUE);
        logger.info("start consume records ");

        for (final ConsumerRecord<String, CoffeeEvent> record : records) {
            eventConsumer.accept(record.value());
        }
        consumer.commitSync();
    }

    public void stop() {
        closed.set(true);
        consumer.wakeup();
    }
}
