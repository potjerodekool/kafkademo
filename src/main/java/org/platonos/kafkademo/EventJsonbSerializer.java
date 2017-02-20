package org.platonos.kafkademo;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import java.util.logging.Logger;

public class EventJsonbSerializer implements JsonbSerializer<CoffeeEvent> {

    Logger logger = LoggerProducer.getLogger(getClass());

    @Override
    public void serialize(final CoffeeEvent event, final JsonGenerator generator, final SerializationContext ctx) {
        logger.info("serialize start");
        generator.write("class", event.getClass().getCanonicalName());
        generator.writeStartObject("data");
        ctx.serialize("data", event, generator);
        generator.writeEnd();
        logger.info("serialize end");
    }

}
