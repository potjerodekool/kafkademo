package org.platonos.kafkademo;

/**
 * Created by Evert on 17-2-2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;

@Controller
public class HelloRest {

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/coffee", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String hello() {
        eventProducer.publish(new CoffeeEvent("black"));
        return "hello: Spring Boot + gradle + java";
    }

}
