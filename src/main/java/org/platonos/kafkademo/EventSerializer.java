package org.platonos.kafkademo;

import org.apache.kafka.common.serialization.Serializer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class EventSerializer implements Serializer<CoffeeEvent> {

    Logger logger = LoggerProducer.getLogger(getClass());

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        // nothing to configure
    }

    @Override
    public byte[] serialize(final String topic, final CoffeeEvent event) {
        logger.info("serialize start");
        if (event == null)
            return null;

        final JsonbConfig config = new JsonbConfig()
                //.withAdapters(new UUIDAdapter())
                .withSerializers(new EventJsonbSerializer());

        try (final Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.toJson(event, CoffeeEvent.class).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("serialize end");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}
