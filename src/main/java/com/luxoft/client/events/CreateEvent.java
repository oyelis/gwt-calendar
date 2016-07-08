package com.luxoft.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.luxoft.shared.EventModel;

public class CreateEvent extends GwtEvent<CreateEventHandler> {

    public CreateEvent(EventModel event) {
        this.event = event;
    }

    private EventModel event;

    public static final Type<CreateEventHandler> TYPE = new Type<CreateEventHandler>();

    @Override
    public Type<CreateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CreateEventHandler handler) {
        handler.create(this);
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }
}
