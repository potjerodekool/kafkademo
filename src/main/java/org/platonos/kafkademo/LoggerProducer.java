package org.platonos.kafkademo;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

public class LoggerProducer {

    public static Logger getLogger(final Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }

    @Produces
    public Logger exposeLogger(InjectionPoint injectionPoint) {
        System.out.println("LOGGER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}