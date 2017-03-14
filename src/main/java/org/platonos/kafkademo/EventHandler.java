package org.platonos.kafkademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by Evert on 23-2-2017.
 */
@Service
public class EventHandler implements ApplicationListener<CoffeeEvent> {

    private EventConsumer eventConsumer;

    ExecutorService mes = Executors.newSingleThreadExecutor();

    @Qualifier("kafkaProperties")
    @Autowired
    Properties kafkaProperties;

    @Autowired
    ApplicationEventPublisher events;

    @Autowired
    private Logger logger;

    @Override
    public void onApplicationEvent(final CoffeeEvent event) {
        System.out.println("make coffee " + event.getName());
    }

    @PostConstruct
    private void init() {
        logger.info("init EventHandler");

        kafkaProperties.put("group.id", "barista-handler");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.publishEvent(ev);
        }, "test");

        mes.execute(eventConsumer);
        logger.info("init EventHandler end");
    }
}
