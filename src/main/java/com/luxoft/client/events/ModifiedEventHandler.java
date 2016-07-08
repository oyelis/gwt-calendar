package com.luxoft.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface ModifiedEventHandler extends EventHandler {
    void onModify(ModifiedEvent event);
}
