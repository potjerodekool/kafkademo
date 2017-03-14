package org.platonos.kafkademo;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Evert on 14-3-2017.
 */
@Configuration
public class Config {

    private Properties kafkaProperties;

    @PostConstruct
    private void init() {
        initKafkaProperties();
    }

    private void initKafkaProperties() {
        try {
            kafkaProperties = new Properties();
            kafkaProperties.load(Config.class.getResourceAsStream("/kafka.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean("kafkaProperties")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Properties exposeKafkaProperties() throws IOException {
        final Properties properties = new Properties();
        properties.putAll(kafkaProperties);
        return properties;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger getLogger(final InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
