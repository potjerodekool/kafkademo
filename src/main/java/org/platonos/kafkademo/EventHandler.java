package org.platonos.kafkademo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Evert on 23-2-2017.
 */
@Singleton
@Startup
public class EventHandler {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<CoffeeEvent> events;

    //@Inject
    //BaristaCommandService baristaService;

    //@Inject
    private Logger logger = LoggerProducer.getLogger(getClass());

    public void handle(@Observes CoffeeEvent event) {
        System.out.println("make coffee " + event.getName());
        //baristaService.makeCoffee(event.getOrderInfo());
    }

    @PostConstruct
    private void init() {
        logger.info("init EventHandler");

        kafkaProperties.put("group.id", "barista-handler");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, "test");

        mes.execute(eventConsumer);
        logger.info("init EventHandler end");
    }
}
