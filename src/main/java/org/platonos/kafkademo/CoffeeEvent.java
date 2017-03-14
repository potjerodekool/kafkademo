package org.platonos.kafkademo;

import org.springframework.context.ApplicationEvent;

import javax.json.JsonNumber;
import javax.json.JsonObject;

/**
 * Created by Evert on 17-2-2017.
 */
public class CoffeeEvent extends ApplicationEvent {

    private final long id;

    private final String name;

    public CoffeeEvent(final String name) {
        super("");
        this.id = System.currentTimeMillis();
        this.name = name;
    }

    public CoffeeEvent(final JsonObject jsonObject) {
        super("");
        this.id = ((JsonNumber) jsonObject.get("id")).longValue();
        this.name = jsonObject.getString("name");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CoffeeEvent that = (CoffeeEvent) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
