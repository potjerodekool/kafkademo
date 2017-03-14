package org.platonos.kafkademo;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.message.config.AuthConfigFactory;

/**
 * Created by Evert on 17-2-2017.
 */
@SpringBootApplication
public class Main {

    public static void main(String... args) throws Exception {
        if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
        SpringApplication.run(Main.class, args);
    }
}
