package org.platonos.kafkademo;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * Created by Evert on 17-2-2017.
 */
public class Main {

    public static void main(String... args) throws Exception {
        Swarm swarm = new Swarm();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addClass(HelloRest.class);
        deployment.addAllDependencies();
        swarm.start().deploy(deployment);

    }
}
