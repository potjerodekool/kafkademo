package org.platonos.kafkademo;

/**
 * Created by Evert on 17-2-2017.
 */

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/coffee")
public class HelloRest {

    @Inject
    private EventProducer eventProducer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        eventProducer.publish(new CoffeeEvent("black"));
        System.out.println("return to user");
        return "hello: wildfly swarm + gradle + java + Coffie";
    }

}
