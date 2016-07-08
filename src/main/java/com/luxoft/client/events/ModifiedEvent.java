package com.luxoft.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.luxoft.shared.EventModel;

import java.util.List;

public class ModifiedEvent extends GwtEvent<ModifiedEventHandler> {

    private List<EventModel> table;

    public ModifiedEvent(List<EventModel> table) {
        this.table = table;
    }

    public List<EventModel> getTable() {
        return table;
    }

    public void setTable(List<EventModel> table) {
        this.table = table;
    }

    public static final Type<ModifiedEventHandler> TYPE = new Type<ModifiedEventHandler>();

    @Override
    public Type<ModifiedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModifiedEventHandler handler) {
        handler.onModify(this);
    }
}
