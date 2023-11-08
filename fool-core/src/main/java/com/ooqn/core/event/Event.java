package com.ooqn.core.event;

public class Event {
    private final   Object sources;

    public Event(Object sources) {
        this.sources = sources;
    }

    public Object getSources() {
        return sources;
    }

}
