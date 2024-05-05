package ru.mark1z11.runbuddies.events;

public class Event {
    private String event_id, event_name;

    public Event(String event_id, String event_name) {
        this.event_id = event_id;
        this.event_name = event_name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }




}
