package org.platonos.kafkademo.serialization;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.platonos.kafkademo.CoffeeEvent;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Evert on 17-2-2017.
 */
public class EventDeserializer implements Deserializer<CoffeeEvent> {

    private static final Logger logger = Logger.getLogger(EventDeserializer.class.getName());

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        // nothing to configure
    }

    @Override
    public CoffeeEvent deserialize(final String topic, final byte[] data) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {

            try (final JsonReader reader = Json.createReader(input)) {
                final JsonObject jsonObject = reader.readObject();
                final Class<? extends CoffeeEvent> eventClass = (Class<? extends CoffeeEvent>) Class.forName(jsonObject.getString("class"));
                return eventClass.getConstructor(JsonObject.class).newInstance(jsonObject.getJsonObject("data"));
            }
        } catch (final Exception e) {
            logger.severe("Could not deserialize event: " + e.getMessage());
            throw new SerializationException("Could not deserialize event", e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}