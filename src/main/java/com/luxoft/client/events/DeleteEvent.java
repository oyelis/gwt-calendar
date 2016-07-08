package com.luxoft.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.luxoft.shared.EventModel;

public class DeleteEvent extends GwtEvent<DeleteEventHandler> {
    public DeleteEvent(EventModel event) {
        this.event = event;
    }

    private EventModel event;

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public static final Type<DeleteEventHandler> TYPE = new Type<DeleteEventHandler>();

    public Type<DeleteEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(DeleteEventHandler handler) {
        handler.onDelete(this);
    }
}
