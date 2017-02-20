package org.platonos.kafkademo;

import javax.json.JsonNumber;
import javax.json.JsonObject;

/**
 * Created by Evert on 17-2-2017.
 */
public class CoffeeEvent {

    private final long id;

    private final String name;

    public CoffeeEvent(final String name) {
        this.id = System.currentTimeMillis();
        this.name = name;
    }

    public CoffeeEvent(final JsonObject jsonObject) {
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
