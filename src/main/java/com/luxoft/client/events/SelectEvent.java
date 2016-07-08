package com.luxoft.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.luxoft.shared.EventModel;

import java.util.Set;

public class SelectEvent extends GwtEvent<SelectEventHandler> {

    private Set<EventModel> set;

    public SelectEvent(Set<EventModel> set) {
        this.set = set;
    }

    public static final Type<SelectEventHandler> TYPE = new Type<SelectEventHandler>();

    @Override
    public Type<SelectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectEventHandler handler) {
        handler.onSelect(this);
    }

    public Set<EventModel> getSet() {
        return set;
    }

    public void setSet(Set<EventModel> set) {
        this.set = set;
    }
}
