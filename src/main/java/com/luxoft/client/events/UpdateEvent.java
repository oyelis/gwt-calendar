package com.luxoft.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.luxoft.shared.EventModel;

public class UpdateEvent extends GwtEvent<UpdateEventHandler> {

    private EventModel eventModel;

    public UpdateEvent(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public static final Type<UpdateEventHandler> TYPE = new Type<UpdateEventHandler>();

    @Override
    public Type<UpdateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateEventHandler handler) {
        handler.onUpdate(this);
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }
}
